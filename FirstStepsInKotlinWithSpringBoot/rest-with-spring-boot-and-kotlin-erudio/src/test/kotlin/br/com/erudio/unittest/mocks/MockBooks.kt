package br.com.erudio.unittests.mapper.mocks

import java.util.ArrayList
import br.com.erudio.data.vo.v1.BooksVO
import br.com.erudio.data.vo.v2.BooksVOV2
import br.com.erudio.model.Books

class MockBooks {

    fun mockEntityList(): ArrayList<Books> {
        val Books: ArrayList<Books> = ArrayList<Books>()
        for (i in 0..13) {
            Books.add(mockEntity(i))
        }
        return Books
    }

    fun mockVOList(): ArrayList<BooksVO> {
        val Books: ArrayList<BooksVO> = ArrayList()
        for (i in 0..13) {
            Books.add(mockVO(i))
        }
        return Books
    }

    fun mockEntity(number: Int) = Books (
        id = number.toLong(),
        author = "Some Author$number",
        price = 25.0,
        title = "Some title$number"
    )

    fun mockVO(number: Int) = BooksVO (
        key = number.toLong(),
        author = "Some Author$number",
        price = 25.0,
        title = "Some title$number"
    )

    fun mockVOV2(number: Int) = BooksVOV2 (
        key = number.toLong(),
        author = "Some Author$number",
        price = 25.0,
        title = "Some title$number"
    )
}