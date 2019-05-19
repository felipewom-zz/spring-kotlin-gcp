package br.com.felipewom.demo.entities

import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity
import org.springframework.data.annotation.Id

@Entity
data class Photo(
        @Id
        var id: String? = null,
        var uri: String? = null,
        var label: String? = null
)