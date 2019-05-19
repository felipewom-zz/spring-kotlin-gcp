package br.com.felipewom.demo.repositories

import br.com.felipewom.demo.entities.Photo
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface PhotoRepository : PagingAndSortingRepository<Photo, String>