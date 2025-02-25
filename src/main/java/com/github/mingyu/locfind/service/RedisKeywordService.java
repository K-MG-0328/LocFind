package com.github.mingyu.locfind.service;

import com.github.mingyu.locfind.dto.KeywordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RedisKeywordService {

    private final RedisTemplate<String, String> redisTemplate;
    private final String key = "keywordRank";

    public void increaseKeywordCnt(String keyword) {
        ZSetOperations<String, String> zsetOps = redisTemplate.opsForZSet();
        zsetOps.incrementScore(key, keyword, 1);
    }

    public List<KeywordDto> searchKeywordRank(){
        ZSetOperations<String, String> zsetOps = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> topList = zsetOps.reverseRangeWithScores(key, 0, 9);
        List<KeywordDto> responses = new ArrayList<>();

        for(ZSetOperations.TypedTuple<String> tuple : topList){
            KeywordDto dto = new KeywordDto();
            String keyword = tuple.getValue();
            Double score = tuple.getScore();
            dto.setKeyword(keyword);
            dto.setScore(score);
            responses.add(dto);
        }

        return responses;
    }

}
