package com.halbae.blog2.model

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp
import javax.persistence.*

@NoArgsConstructor
@AllArgsConstructor
@Entity
data class Reply(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(nullable = false, length = 200)
  var content: String? = null,

  @ManyToOne
  @JoinColumn(name = "boardId")
  var board: Board? = null,

  @ManyToOne
  @JoinColumn(name = "userId")
  var user: User? = null,

  @CreationTimestamp
  val createdAt: Timestamp? = null
)