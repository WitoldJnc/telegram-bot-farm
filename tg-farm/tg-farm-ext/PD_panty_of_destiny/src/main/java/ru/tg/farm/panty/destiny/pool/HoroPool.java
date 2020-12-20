package ru.tg.farm.panty.destiny.pool;

import ru.tg.farm.panty.destiny.model.Sign;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.tg.farm.panty.destiny.model.Sign.*;

@Component
public class HoroPool{

    private Map<Sign, List<String>> horos = new HashMap<Sign, List<String>>() {{
        put(ARIES, Arrays.asList(
                "https://orakul.com/horoscope/astrologic/more/aries/today.html",
                "https://1001goroskop.ru/?znak=aries",
                "https://www.wday.ru/horoscope/common/oven/daily/",
                "https://goroskop365.ru/aries/"
        ));
        put(TAURUS, Arrays.asList(
                "https://orakul.com/horoscope/astrologic/more/taurus/today.html",
                "https://1001goroskop.ru/?znak=taurus",
                "https://www.wday.ru/horoscope/common/telec/daily/",
                "https://goroskop365.ru/taurus/"
        ));
        put(CANCER, Arrays.asList(
                "https://orakul.com/horoscope/astrologic/more/cancer/today.html",
                "https://1001goroskop.ru/?znak=cancer",
                "https://www.wday.ru/horoscope/common/rak/daily/",
                "https://goroskop365.ru/cancer/"
        ));
        put(GEMINI, Arrays.asList(
                "https://orakul.com/horoscope/astrologic/more/gemini/today.html",
                "https://1001goroskop.ru/?znak=gemini",
                "https://www.wday.ru/horoscope/common/bliznecy/daily/",
                "https://goroskop365.ru/gemini/"
        ));
        put(LEO, Arrays.asList(
                "https://orakul.com/horoscope/astrologic/more/lion/today.html",
                "https://1001goroskop.ru/?znak=leo",
                "https://www.wday.ru/horoscope/common/lev/daily/",
                "https://goroskop365.ru/leo/"
        ));
        put(VIGRO, Arrays.asList(
                "https://orakul.com/horoscope/astrologic/more/virgo/today.html",
                "https://1001goroskop.ru/?znak=virgo",
                "https://www.wday.ru/horoscope/common/deva/daily/",
                "https://goroskop365.ru/virgo/"
        ));
        put(LIBRA, Arrays.asList(
                "https://orakul.com/horoscope/astrologic/more/libra/today.html",
                "https://1001goroskop.ru/?znak=libra",
                "https://www.wday.ru/horoscope/common/vesy/daily/",
                "https://goroskop365.ru/libra/"
        ));
        put(SCORPIO, Arrays.asList(
                "https://orakul.com/horoscope/astrologic/more/scorpio/today.html",
                "https://1001goroskop.ru/?znak=scorpio",
                "https://www.wday.ru/horoscope/common/skorpion/daily/",
                "https://goroskop365.ru/scorpio/"
        ));
        put(SAGGITARIUS, Arrays.asList(
                "https://orakul.com/horoscope/astrologic/more/sagittarius/today.html",
                "https://1001goroskop.ru/?znak=sagittarius",
                "https://www.wday.ru/horoscope/common/strelec/daily/",
                "https://goroskop365.ru/sagittarius/"
        ));
        put(CAPRICORN,Arrays.asList(
                "https://orakul.com/horoscope/astrologic/more/capricorn/today.html",
                "https://1001goroskop.ru/?znak=capricorn",
                "https://www.wday.ru/horoscope/common/kozerog/daily/",
                "https://goroskop365.ru/capricorn/"
        ));
        put(AQUARIUS,Arrays.asList(
                "https://orakul.com/horoscope/astrologic/more/aquarius/today.html",
                "https://1001goroskop.ru/?znak=aquarius",
                "https://www.wday.ru/horoscope/common/vodolej/daily/",
                "https://goroskop365.ru/aquarius/"
        ));
        put(PISCES,Arrays.asList(
                "https://orakul.com/horoscope/astrologic/more/pisces/today.html",
                "https://1001goroskop.ru/?znak=pisces",
                "https://www.wday.ru/horoscope/common/ryby/daily/",
                "https://goroskop365.ru/pisces/"
        ));
    }};

    private Map<Sign, String> shiza = new HashMap<Sign, String>() {{
        put(ARIES, "https://www.predskazanie.ru/daily_horoscope/?day=&s=1");
        put(TAURUS, "https://www.predskazanie.ru/daily_horoscope/?day=&s=2");
        put(CANCER, "https://www.predskazanie.ru/daily_horoscope/?day=&s=4");
        put(GEMINI, "https://www.predskazanie.ru/daily_horoscope/?day=&s=3");
        put(LEO, "https://www.predskazanie.ru/daily_horoscope/?day=&s=5");
        put(VIGRO, "https://www.predskazanie.ru/daily_horoscope/?day=&s=6");
        put(LIBRA, "https://www.predskazanie.ru/daily_horoscope/?day=&s=7");
        put(SCORPIO, "https://www.predskazanie.ru/daily_horoscope/?day=&s=8");
        put(SAGGITARIUS, "https://www.predskazanie.ru/daily_horoscope/?day=&s=9");
        put(CAPRICORN, "https://www.predskazanie.ru/daily_horoscope/?day=&s=10");
        put(AQUARIUS, "https://www.predskazanie.ru/daily_horoscope/?day=&s=11");
        put(PISCES, "https://www.predskazanie.ru/daily_horoscope/?day=&s=12");
    }};

    public List<Sign> getInitList() {
        return Stream.of(ARIES, TAURUS, GEMINI, CANCER,
                LEO, VIGRO, LIBRA, SCORPIO, SAGGITARIUS, CAPRICORN, AQUARIUS, PISCES
        )
                .collect(Collectors.toList());
    }

    public Sign getRandomSign() {
        Random random = new Random();
        return getInitList().get(random.nextInt(getInitList().size()));
    }

    public Map<Sign, List<String>> getHoros () {
        return horos;
    }

    public Map<Sign, String> getShiza () {
        return shiza;
    }

}
