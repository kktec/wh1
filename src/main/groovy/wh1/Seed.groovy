package wh1

import grails.gorm.annotation.Entity
import groovy.transform.ToString

@Entity
@ToString
class Seed {

    Strain strain

    static mapping = {
        strain fetch: 'join'
    }

}
