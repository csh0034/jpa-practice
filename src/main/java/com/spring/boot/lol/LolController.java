package com.spring.boot.lol;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LolController {

    private static final String API_KEY = "RGAPI-21c57ac8-db18-4954-b779-b06cfcc77443";
    private static final String END_POINT = "kr.api.riotgames.com";

    private final RestTemplate restTemplate;

}
/*
{
    "id": "hjBjVxl1-LE4QtQIWBR_vKZg8t5pVccxC5RHvVLqA5fQqsg",
    "accountId": "5I1YpiBM9AkQB6lZVlYq9LBTFuR06cH-lve8S1_jnQ30Im0",
    "puuid": "Mu2d4ab4Pil0JLYSpsN417p1Vc8xnZ77mXgWgLWMgTKO48z5gyURIUA-fpobKzZ1DiQCgILW5l768A",
    "name": "퓨리오싸",
    "profileIconId": 4840,
    "revisionDate": 1619329142000,
    "summonerLevel": 108
}

{
    "id": "HN88rwojEUSKbH5j1QLumfmATsY3hcAMCRAfWR2zR5gC6fI",
    "accountId": "nj2qsf4LmD7__oUCFLz231IHowoYNlW1Onarv5zeBbbyqQY",
    "puuid": "4OAJpLbcKSGfKp6UIMvrk6PkUiwLXVgHSiQywxGHh2_X72BpatlfJiJo9I0LjpkjxCR49Wu8Y6hJBA",
    "name": "돌아온박재범",
    "profileIconId": 608,
    "revisionDate": 1618295654000,
    "summonerLevel": 94
}

{
    "id": "1hLuZjEEhW8kjB18nGf1BaV_IwCmKvLnCfZB3HQJORdib5Q",
    "accountId": "A3jVqRosaeLIGUzXVYyWK3bgBzyOpGNpW1O1OeG82ycbDDb3UsWMZw-k",
    "puuid": "_Xvbq4ahxWSi0tJ8UfS1eML_3MrLAcNleHHDI3IC0z1IRTCR68KRfDd2YXP3ItGi3ow-owI33WNnkQ",
    "name": "최강시크남",
    "profileIconId": 3542,
    "revisionDate": 1619329142000,
    "summonerLevel": 29
}
 */