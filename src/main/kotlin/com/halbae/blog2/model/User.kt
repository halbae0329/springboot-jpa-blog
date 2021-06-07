package com.halbae.blog2.model

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp
import javax.persistence.*

@NoArgsConstructor
@AllArgsConstructor
@Entity
//@DynamicInsert insert시 null 인 필드는 제외
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 100, unique = true)
    var username: String? = null,

    @Column(nullable = false, length = 100)
    var password: String? = null,

    @Column(nullable = false, length = 50)
    var email: String? = null,

    //@ColumnDefault("user")
    //DB는 RolyType 이 없다.
    @Enumerated(EnumType.STRING)
    var role: RolyType? = null,

    var oauth: String? = null,

    @CreationTimestamp
    val createdAt: Timestamp? = null
)
