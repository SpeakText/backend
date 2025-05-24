package com.speaktext.backend.book.selling_book.domain;

import com.speaktext.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ReadingProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long readingProgressId;

    private Long currentIndex;

    @ManyToOne
    private Member member;

    @ManyToOne
    private SellingBook sellingBook;

    public ReadingProgress(Long currentIndex, Member member, SellingBook sellingBook) {
        this.currentIndex = currentIndex;
        this.member = member;
        this.sellingBook = sellingBook;
    }

    public void updateCurrentIndex(Long currentIndex) {
        this.currentIndex = currentIndex;
    }

}
