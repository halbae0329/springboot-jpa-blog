package com.halbae.blog2.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp
import javax.persistence.*

@NoArgsConstructor
@AllArgsConstructor
@Entity
data class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(nullable = false, length = 100)
    var title: String,

    @Lob
    var content: String,

    var count: Int?,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    var user: User?,

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
    @JsonIgnoreProperties("board")
    @OrderBy("id desc")
    val replies: List<Reply>?,

    @CreationTimestamp
    val createdAt: Timestamp?
)
