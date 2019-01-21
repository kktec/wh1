package wh1

import grails.gorm.annotation.Entity
import groovy.transform.ToString

@Entity
@ToString
class Strain {

    String code

    static constraints = {
        code blank: false, minSize: 2, maxSize: 10, unique: true
    }

}
