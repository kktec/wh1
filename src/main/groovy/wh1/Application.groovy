package wh1

import groovy.util.logging.Slf4j
import io.micronaut.runtime.Micronaut
import groovy.transform.CompileStatic

@Slf4j
@CompileStatic
class Application {
    static void main(String[] args) {
        Micronaut.run(Application)

        initData()

        Plant.withTransaction {
            println Plant.where { seed.strain.code == 'AH' }.list().name
            println Plant.where { mother.seed.strain.code == 'AH' }.list().name
            println Plant.where { seed.strain.code == 'AH' || mother.seed.strain.code == 'AH' }.list().name
        }
    }

    private static void initData() {
        Strain ah = new Strain(code: 'AH')
        Strain gc = new Strain(code: 'GC')
        Strain.withTransaction {
            ah.save()
            gc.save()
        }

        Seed s1 = new Seed(strain: ah)
        Seed s2 = new Seed(strain: ah)
        Seed s3 = new Seed(strain: gc)
        Seed.withTransaction {
            s1.save()
            s2.save()
            s3.save()
        }

        Plant ah1 = new Plant(name: 'ah1', seed: s1)
        Plant ah2 = new Plant(name: 'ah2', seed: s2)
        Plant gc1 = new Plant(name: 'gc1', seed: s3)
        Plant gc2 = new Plant(name: 'gc2', mother: gc1)
        Plant ah3 = new Plant(name: 'ah3', mother: ah1)
        Plant.withTransaction {
            ah1.save()
            ah2.save()
            gc1.save()
            gc2.save()
            ah3.save()
            log.info("Strain count = $Strain.count")
            log.info("Seed count = $Seed.count")
            log.info("Plant count = $Plant.count")
        }
    }
}