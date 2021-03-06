package com.github.fairsearch.deltr.models;

import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DeltrTopDocsImpl extends TopDocs implements DeltrTopDocs {

    private int questionId;

    public DeltrTopDocsImpl(int questionId) {
        super(0, new ScoreDoc[0], 0);
    }
    public DeltrTopDocsImpl(int questionId, long totalHits, ScoreDoc[] scoreDocs, float maxScore) {
        super(totalHits, scoreDocs, maxScore);
        this.questionId = questionId;
    }

    @Override
    public void reorder() {
        Arrays.sort(this.scoreDocs, (o1, o2) -> {
            if(o1.score < o2.score)
                return 1;
            else if(o1.score > o2.score)
                return -1;
            return 0;
        });
    }

    @Override
    public int id() {
        return this.questionId;
    }

    @Override
    public int size() {
        return this.scoreDocs.length;
    }

    @Override
    public DeltrDoc doc(int index) {
        return (DeltrDoc) this.scoreDocs[index];
    }

    @Override
    public void put(DeltrDoc[] docs) {
        this.scoreDocs = new ScoreDoc[docs.length];
        IntStream.range(0, docs.length).forEach((i) -> {
            this.scoreDocs[i] = (DeltrDocImpl) docs[i];
        });
    }
}
