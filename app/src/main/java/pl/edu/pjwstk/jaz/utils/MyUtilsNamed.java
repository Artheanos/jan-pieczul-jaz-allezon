package pl.edu.pjwstk.jaz.utils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class MyUtilsNamed {
    static public String addSlashes(String inp) {
        return inp.replace("\"", "\\\"").replace("\'", "\\\'");
    }
}
