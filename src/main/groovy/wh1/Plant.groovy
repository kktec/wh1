package wh1

import grails.gorm.annotation.Entity
import groovy.transform.ToString
import org.springframework.validation.Errors

@Entity
@ToString
class Plant {

    String name

    Seed seed

    Plant mother

    static constraints = {
        name blank: false, minSize: 3, maxSize: 25, unique: true
        seed nullable: true, validator: { Seed seed, Plant plant, Errors errors ->
            if (!seed && !plant.mother) {
                errors.rejectValue('seed', 'nullable')
            }
        }
        mother nullable: true, validator: { Plant mother, Plant plant, Errors errors ->
            if (!mother && !plant.seed) {
                errors.rejectValue('mother', 'nullable')
            }
            if (mother && plant.seed) {
                errors.rejectValue('mother', 'invalid.clone')
            }
        }
    }

    static mapping = {
        seed fetch: 'join'
        mother fetch: 'join'
    }
}
