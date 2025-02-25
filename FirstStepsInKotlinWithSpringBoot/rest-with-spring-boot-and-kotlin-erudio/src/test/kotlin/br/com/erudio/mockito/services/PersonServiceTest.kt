package br.com.erudio.mockito.services

import br.com.erudio.exceptions.RequeriedObjectIsNullException
import br.com.erudio.repository.PersonRepository
import br.com.erudio.services.PersonService
import br.com.erudio.unittests.mapper.mocks.MockPerson
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class PersonServiceTest {

    private lateinit var inputObject: MockPerson

    @InjectMocks
    private lateinit var service: PersonService

    @Mock
    private lateinit var repository: PersonRepository

    @BeforeEach
    fun setUpMock() {
        inputObject = MockPerson()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findAll() {
        val people = inputObject.mockEntityList()
        `when`(repository.findAll()).thenReturn(people)

        val response = service.findAll()

        assertNotNull(response)
        assertEquals(14, response.size)

        val personOne = response[1]

        assertNotNull(personOne)
        assertNotNull(personOne.key)
        assertNotNull(personOne.links)
        assertTrue(personOne.links.toString().contains("/person/1"))
        assertEquals("Address Test1", personOne.address)
        assertEquals("First Name Test1", personOne.firstName)
        assertEquals("Last Name Test1", personOne.lastName)
        assertEquals("Female", personOne.gender)

        val personFour = response[4]

        assertNotNull(personFour)
        assertNotNull(personFour.key)
        assertNotNull(personFour.links)
        assertTrue(personFour.links.toString().contains("/person/4"))
        assertEquals("Address Test4", personFour.address)
        assertEquals("First Name Test4", personFour.firstName)
        assertEquals("Last Name Test4", personFour.lastName)
        assertEquals("Male", personFour.gender)
    }

    @Test
    fun findById() {
        val person = inputObject.mockEntity(1)
        person.id = 1L
        `when`(repository.findById(1)).thenReturn(Optional.of(person))

        val response = service.findById(1)

        assertNotNull(response)
        assertNotNull(response.key)
        assertNotNull(response.links)

        assertTrue(response.links.toString().contains("/person/1"))

        assertEquals("Address Test1", response.address)
        assertEquals("First Name Test1", response.firstName)
        assertEquals("Last Name Test1", response.lastName)
        assertEquals("Female", response.gender)
    }

    @Test
    fun create() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(repository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(1)
        val response = service.create(vo)

        assertNotNull(response)
        assertNotNull(response.key)
        assertNotNull(response.links)
        assertTrue(response.links.toString().contains("/person/1"))
        assertEquals("Address Test1", response.address)
        assertEquals("First Name Test1", response.firstName)
        assertEquals("Last Name Test1", response.lastName)
        assertEquals("Female", response.gender)
    }


    @Test
    fun createWithNullPerson(){
        val exception: Exception = assertThrows(
            RequeriedObjectIsNullException::class.java
        ){service.create(null)}

        val expectedMessage = "It is not allowed to persist a null object"
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(actualMessage))
    }

    @Test
    fun createV2() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(repository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVOV2(1)
        val response = service.createV2(vo)

        assertNotNull(response)
        assertNotNull(response.key)
        assertNotNull(response.links)
        assertTrue(response.links.toString().contains("/person/1"))
        assertEquals("Address Test1", response.address)
        assertEquals("First Name Test1", response.firstName)
        assertEquals("Last Name Test1", response.lastName)
        assertEquals("Female", response.gender)
    }

    @Test
    fun update() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
        `when`(repository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(1)
        val response = service.update(1,vo)

        assertNotNull(response)
        assertNotNull(response.key)
        assertNotNull(response.links)
        assertTrue(response.links.toString().contains("/person/1"))
        assertEquals("Address Test1", response.address)
        assertEquals("First Name Test1", response.firstName)
        assertEquals("Last Name Test1", response.lastName)
        assertEquals("Female", response.gender)
    }

    @Test
    fun updateWithNullPerson(){
        val exception: Exception = assertThrows(
            RequeriedObjectIsNullException::class.java
        ){service.update(1,null)}

        val expectedMessage = "It is not allowed to persist a null object"
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(actualMessage))
    }

    @Test
    fun delete() {
        val entity = inputObject.mockEntity(1)
        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
        service.delete(1)
    }
}