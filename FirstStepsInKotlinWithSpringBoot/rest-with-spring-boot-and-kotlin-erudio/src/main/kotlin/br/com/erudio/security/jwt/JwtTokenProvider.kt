package br.com.erudio.security.jwt

import br.com.erudio.data.vo.v1.TokenVO
import br.com.erudio.exceptions.InvalidJwtAuthenticationException
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

@Service
class JwtTokenProvider {

    @Value("\${security.jwt.token.secret-key:secret}")
    private var secretKey = "secret"

    @Value("\${security.jwt.token.expire-length:3600000}")
    private var validityInMilliseconds: Long = 3_600_000 //1h

    @Autowired
    private lateinit var userDetails: UserDetailsService

    private lateinit var algorithm: Algorithm

    @PostConstruct
    protected fun init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
        algorithm = Algorithm.HMAC256(secretKey.toByteArray())
    }

    fun createAccessToken(username: String, roles: List<String?>): TokenVO {
        val nowDate = Date()
        val valid = Date(nowDate.time + validityInMilliseconds)
        val accessToken = getAccessToken(username, roles, nowDate, valid)
        val refreshToken = getRefreshToke(username, roles, nowDate)

        return TokenVO(
            username = username,
            authenticated = true,
            created = nowDate,
            expiration = valid,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    private fun getAccessToken(username: String, roles: List<String?>, nowDate: Date, valid: Date): String {
        val issuerURL: String  = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
        return JWT.create()
            .withClaim("roles", roles)
            .withIssuedAt(nowDate)
            .withExpiresAt(valid)
            .withSubject(username)
            .withIssuer(issuerURL)
            .sign(algorithm)
            .trim()

    }

    private fun getRefreshToke(username: String, roles: List<String?>, nowDate: Date): String? {
        val validRefreshToken = Date(nowDate.time + validityInMilliseconds * 3)

        return JWT.create()
            .withClaim("roles", roles)
            .withIssuedAt(nowDate)
            .withExpiresAt(validRefreshToken)
            .withSubject(username)
            .sign(algorithm)
            .trim()
    }

    fun getAuthentication(token: String): Authentication {
        val decodeJWT: DecodedJWT = decodedToken(token)
        val userDetails: UserDetails = userDetails.loadUserByUsername(decodeJWT.subject)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun decodedToken(token: String): DecodedJWT {
        val algorithm = Algorithm.HMAC256(secretKey.toByteArray())
        val verifier: JWTVerifier = JWT.require(algorithm).build()
        return verifier.verify(token)
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if(!bearerToken.isNullOrBlank() && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring("Bearer ".length)
        } else null
    }

    fun validateToken(token: String): Boolean {
        val decodedJWT = decodedToken(token)
        try{
            if (decodedJWT.expiresAt.before(Date())) false
            return true
        } catch (e: Exception) {
            throw InvalidJwtAuthenticationException("Expired or Invalid")
        }

    }
}