package br.com.erudio.services

import br.com.erudio.controller.BooksController
import br.com.erudio.data.vo.v1.BooksVO
import br.com.erudio.exceptions.RequeriedObjectIsNullException
import br.com.erudio.exceptions.ResourceNotFoundException
import br.com.erudio.mapper.DozerMapper
//import br.com.erudio.mapper.custom.BooksMapper
import br.com.erudio.model.Books
import br.com.erudio.repository.BooksRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger
import br.com.erudio.data.vo.v2.BooksVOV2 as BooksVOV2

@Service
class BooksService {
    @Autowired(required=false)
    private lateinit var repository: BooksRepository
//    @Autowired(required=false)
//    private lateinit var mapper: BooksMapper
    private val logger = Logger.getLogger(BooksService::class.java.name)

    fun findAll(): List<BooksVO> {
        logger.info("Finding all Books!")
        val books = repository.findAll()
        val vos = DozerMapper.parseListObjects(books, BooksVO::class.java)
        for(booksT in vos){
            val withSelfRel = linkTo(BooksController::class.java).slash(booksT.key).withSelfRel()
            booksT.add(withSelfRel)
        }
        return vos
    }

    fun findById(id: Long): BooksVO {
        logger.info("Finding one Books with ID $id!")
        val Books = repository.findById(id)
            .orElseThrow{ResourceNotFoundException("No records found for this id")}
        val BooksVO: BooksVO = DozerMapper.parseObject(Books, BooksVO::class.java)
        val withSelfRel = linkTo(BooksController::class.java).slash(BooksVO.key).withSelfRel()
        BooksVO.add(withSelfRel)
        return BooksVO
    }

    fun create(books: BooksVO?): BooksVO {
        if (books == null) throw RequeriedObjectIsNullException()
        logger.info("Create one Books with title ${books.title}! ")
        val entity:Books =  DozerMapper.parseObject(books, Books::class.java)
        val BooksVO: BooksVO = DozerMapper.parseObject(repository.save(entity), BooksVO::class.java)
        val withSelfRel = linkTo(BooksController::class.java).slash(BooksVO.key).withSelfRel()
        BooksVO.add(withSelfRel)
        return BooksVO
    }

    fun createV2(books: BooksVOV2?): BooksVOV2 {
        if (books == null) throw RequeriedObjectIsNullException()
        logger.info("Create one Books with title ${books.title}! ")
        val entity:Books =  DozerMapper.parseObject(books, Books::class.java)
        val BooksVOV2: BooksVOV2 = DozerMapper.parseObject(repository.save(entity), BooksVOV2::class.java)
        val withSelfRel = linkTo(BooksController::class.java).slash(BooksVOV2.key).withSelfRel()
        BooksVOV2.add(withSelfRel)
        return BooksVOV2
    }

    fun update(id: Long, books: BooksVO?): BooksVO {
        if (books == null) throw RequeriedObjectIsNullException()
        logger.info("Updating one Books with id ${id}! ")
        val entity = repository.findById(id)
            .orElseThrow{ResourceNotFoundException("No records found for this id")}

        entity.title = books.title
        entity.author = books.author
        entity.price = books.price
        entity.launch_date = books.launch_date

        val BooksVO: BooksVO = DozerMapper.parseObject(repository.save(entity), BooksVO::class.java)
        val withSelfRel = linkTo(BooksController::class.java).slash(BooksVO.key).withSelfRel()
        BooksVO.add(withSelfRel)
        return BooksVO
    }

    fun delete(id: Long){
        logger.info("Deleting one Books with id ${id}! ")
        val entity = repository.findById(id)
            .orElseThrow{ResourceNotFoundException("No records found for this id")}
        repository.delete(entity)
    }
}