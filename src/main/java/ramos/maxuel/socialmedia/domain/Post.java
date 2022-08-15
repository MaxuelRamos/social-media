package ramos.maxuel.socialmedia.domain;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Post")
public class Post {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 777)
    private String message;

    @Column
    private ZonedDateTime timestamp;

    @Column
    private Long authorId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "author_id", insertable = false, updatable = false)
//    private User author;

    @Column(name = "reference_post_id")
    private Long referencePostId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "reference_post_id", insertable = false, updatable = false)
//    private Post referencePost;

    @Transient
    public PostType getType() {
        return referencePostId == null ? PostType.ORIGINAL :
                message == null ? PostType.REPOST :
                        PostType.QUOTE_POST;
    }
}
