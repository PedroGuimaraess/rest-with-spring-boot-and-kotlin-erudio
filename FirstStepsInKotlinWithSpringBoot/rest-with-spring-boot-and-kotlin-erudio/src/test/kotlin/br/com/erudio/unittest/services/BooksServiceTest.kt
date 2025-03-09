package br.com.erudio.unittest.services

import br.com.erudio.exceptions.RequeriedObjectIsNullException
import br.com.erudio.repository.BooksRepository
import br.com.erudio.services.BooksService
import br.com.erudio.unittests.mapper.mocks.MockBooks
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
class BooksServiceTest {

    private lateinit var inputObject: MockBooks

    @InjectMocks
    private lateinit var service: BooksService

    @Mock
    private lateinit var repository: BooksRepository

    @BeforeEach
    fun setUpMock() {
        inputObject = MockBooks()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findAll() {
        val books = inputObject.mockEntityList()
        `when`(repository.findAll()).thenReturn(books)

        val response = service.findAll()

        assertNotNull(response)
        assertEquals(14, response.size)

        val BooksOne = response[1]

        assertNotNull(BooksOne)
        assertNotNull(BooksOne.key)
        assertNotNull(BooksOne.links)
        assertTrue(BooksOne.links.toString().contains("/api/Books/v1/1"))
        assertEquals("Some title1", BooksOne.title)
        assertEquals("Some Author1", BooksOne.author)
        assertEquals(25.0, BooksOne.price)

        val BooksFour = response[4]

        assertNotNull(BooksFour)
        assertNotNull(BooksFour.key)
        assertNotNull(BooksFour.links)
        assertTrue(BooksFour.links.toString().contains("/api/Books/v1/4"))
        assertEquals("Some title4", BooksFour.title)
        assertEquals("Some Author4", BooksFour.author)
        assertEquals(25.0, BooksFour.price)
    }

    @Test
    fun findById() {
        val Books = inputObject.mockEntity(1)
        Books.id = 1L
        `when`(repository.findById(1)).thenReturn(Optional.of(Books))

        val response = service.findById(1)

        assertNotNull(response)
        assertNotNull(response.key)
        assertNotNull(response.links)

        assertTrue(response.links.toString().contains("/api/Books/v1/1"))

        assertEquals("Some title1", response.title)
        assertEquals("Some Author1", response.author)
        assertEquals(25.0, response.price)
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
        assertTrue(response.links.toString().contains("/api/Books/v1/1"))
        assertEquals("Some title1", response.title)
        assertEquals("Some Author1", response.author)
        assertEquals(25.0, response.price)
    }


    @Test
    fun createWithNullBooks(){
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
        assertTrue(response.links.toString().contains("/api/Books/v1/1"))
        assertEquals("Some title1", response.title)
        assertEquals("Some Author1", response.author)
        assertEquals(25.0, response.price)
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
        assertTrue(response.links.toString().contains("/api/Books/v1/1"))
        assertEquals("Some title1", response.title)
        assertEquals("Some Author1", response.author)
        assertEquals(25.0, response.price)
    }

    @Test
    fun updateWithNullBooks(){
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