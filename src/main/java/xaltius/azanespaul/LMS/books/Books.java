package xaltius.azanespaul.LMS.books;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xaltius.azanespaul.LMS.users.Users;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private Boolean borrowed;

    @ManyToOne
    private Users borrowedBy;
}
