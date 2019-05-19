package com.ssaw.ssawdataelasticsearchdemo.repository;

import com.ssaw.ssawdataelasticsearchdemo.document.CatDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author HuSen
 * @date 2019/3/8 11:09
 */
@Repository
public interface CatRepository extends ElasticsearchRepository<CatDocument, Long> {
}