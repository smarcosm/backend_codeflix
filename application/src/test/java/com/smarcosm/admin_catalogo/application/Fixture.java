package com.smarcosm.admin_catalogo.application;

import com.github.javafaker.Faker;
import com.smarcosm.admin_catalogo.domain.castmember.CastMember;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberType;
import com.smarcosm.admin_catalogo.domain.category.Category;
import com.smarcosm.admin_catalogo.domain.genre.Genre;
import com.smarcosm.admin_catalogo.domain.video.Rating;
import com.smarcosm.admin_catalogo.domain.video.Resource;
import com.smarcosm.admin_catalogo.domain.video.Video;

import java.time.Year;
import java.util.Set;

import static io.vavr.API.*;

public final class Fixture {
    private static final Faker FAKER = new Faker();

    public static String name() {
        return FAKER.name().fullName();
    }
    public static Integer year() {
        return FAKER.random().nextInt(2010, 2030);
    }
    public static Double duration() {
        return FAKER.options().option(120.0, 15.5, 35.5, 10.0, 2.5);
    }
    public static boolean bool() {
        return FAKER.bool().bool();
    }

    public static String title() {
        return FAKER.options().option(
                "O que são microsserviços? Uma abordagem arquitetônica e organizacional",
                "Microsserviços: Desenvolvendo com independência e escalabilidade",
                "Arquitetura de Microsserviços: Agilidade e flexibilidade",
                "Microsserviços na prática: Como dividir um aplicativo",
                "Benefícios dos Microsserviços: Agilidade e throughput"
        );
    }
    public static Video video(){
        return Video.newVideo(
                Fixture.title(),
                Videos.description(),
                Year.of(Fixture.year()),
                Fixture.duration(),
                Fixture.bool(),
                Fixture.bool(),
                Videos.rating(),
                Set.of(Categories.aulas().getId()),
                Set.of(Genres.tech().getId()),
                Set.of(CastMembers.sebastiao().getId())

        );
    }
    public static final class Categories {
        private static final Category AULAS= Category.newCategory("Aulas", "Some description", true);
        public static Category aulas() {
            return AULAS.clone();
        }
    }
    public static final class CastMembers {
        private static final CastMember WESLEY = CastMember.newMember("Wesley FullCycle", CastMemberType.ACTOR);
        private static final CastMember SEBASTIAO = CastMember.newMember("Sebastião FullCycle", CastMemberType.ACTOR);
        public static CastMemberType type() {
            return FAKER.options().option(CastMemberType.values());
        }
        public static CastMember wesley(){ return CastMember.with(WESLEY); }
        public static CastMember sebastiao(){ return CastMember.with(SEBASTIAO); }
    }
    public static final class Genres {
        private static final Genre  TECH = Genre.newGenre("Technology", true);
        public static Genre tech(){
            return Genre.with(TECH);
        }
    }
    public static final class Videos {
        private static final Video SYSTEM_DESIGN =
                Video.newVideo(
                "O que são microsserviços? Uma abordagem arquitetônica e organizacional",
                description(),
                Year.of(2024),
                Fixture.duration(),
                Fixture.bool(),
                Fixture.bool(),
                rating(),
                Set.of(Categories.aulas().getId()),
                Set.of(Genres.tech().getId()),
                Set.of(CastMembers.sebastiao().getId())
        );

        public static Video systemDesign(){
            return Video.with(SYSTEM_DESIGN);
        }
        public static Rating rating() {
           return FAKER.options().option(Rating.values());
        }
        public static Resource resource(final Resource.Type type) {
            final String contentType = Match(type).of(
                Case($(List(Resource.Type.VIDEO, Resource.Type.TRAILER)::contains), "video/mp4"),
                    Case($(), "image/jpg")
            );
            final byte[] content = "Conteudo".getBytes();
            return Resource.with(content, contentType, type.name().toLowerCase(), type);
        }

        public static String description() {
            return FAKER.options().option(
                    """
                    Microserviços: Entenda o que são microserviços e como eles revolucionam o desenvolvimento de aplicativos. 
                    Neste vídeo, exploramos os conceitos fundamentais de microserviços, suas vantagens e desafios, 
                    bem como exemplos práticos de implementação.
                    """,
                    """
                    Domain-Driven Design (DDD): Descubra como o Domain-Driven Design (DDD) pode melhorar a qualidade e a manutenção do seu código. 
                    Este vídeo explica os blocos de construção do DDD, como modelar domínios, 
                    estratégias táticas e padrões comuns usados na implementação.
                    """,
                    """                                                                             
                    Paralelismo e Concorrência: Aprenda a diferença entre paralelismo e concorrência na programação. 
                    Este vídeo esclarece como esses conceitos se relacionam, 
                    quando usá-los e como otimizar o desempenho de aplicativos multithread.
                    """
            );
        }
    }
}
