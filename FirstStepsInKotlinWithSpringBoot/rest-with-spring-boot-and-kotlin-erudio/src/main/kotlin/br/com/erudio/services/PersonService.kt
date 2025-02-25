package br.com.erudio.services

import br.com.erudio.controller.PersonController
import br.com.erudio.data.vo.v1.PersonVO
import br.com.erudio.exceptions.ResourceNotFoundException
import br.com.erudio.mapper.DozerMapper
import br.com.erudio.mapper.custom.PersonMapper
import br.com.erudio.model.Person
import br.com.erudio.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger
import br.com.erudio.data.vo.v2.PersonVO as PersonVOV2

@Service
class PersonService {
    @Autowired(required=false)
    private lateinit var repository: PersonRepository
    @Autowired(required=false)
    private lateinit var mapper: PersonMapper
    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<PersonVO> {
        logger.info("Finding all people!")
        val people = repository.findAll()
        val vos = DozerMapper.parseListObjects(people, PersonVO::class.java)
        for(persons in vos){
            val withSelfRel = linkTo(PersonController::class.java).slash(persons.key).withSelfRel()
            persons.add(withSelfRel)
        }
        return vos
    }

    fun findById(id: Long): PersonVO {
        logger.info("Finding one person with ID $id!")
        val person = repository.findById(id)
            .orElseThrow{ResourceNotFoundException("No records found for this id")}
        val personVO: PersonVO = DozerMapper.parseObject(person, PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun create(person: PersonVO): PersonVO {
        logger.info("Create one person with first name ${person.firstName}! ")
        val entity:Person =  DozerMapper.parseObject(person, Person::class.java)
        val personVO: PersonVO = DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun createV2(person: PersonVOV2): PersonVOV2 {
        logger.info("Create one person with first name ${person.firstName}! ")
        val entity:Person = mapper.mapVOToEntity(person)
        return mapper.mapEntityToVO(repository.save(entity))
    }

    fun update(id: Long, person: PersonVO): PersonVO {
        logger.info("Updating one person with id ${id}! ")
        val entity = repository.findById(id)
            .orElseThrow{ResourceNotFoundException("No records found for this id")}

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender

        val personVO: PersonVO = DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun delete(id: Long){
        logger.info("Deleting one person with id ${id}! ")
        val entity = repository.findById(id)
            .orElseThrow{ResourceNotFoundException("No records found for this id")}
        repository.delete(entity)
    }
}