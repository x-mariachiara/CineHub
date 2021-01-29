package com.unisa.cinehub.views.component;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.Image;

@Tag("video")
public class Video extends HtmlContainer implements ClickNotifier<Image> {

    private static final PropertyDescriptor<String, String> srcDescriptor = PropertyDescriptors.attributeWithDefault("src", "");

    public Video(){
        super();
        getElement().setProperty("controls", true);
    }

    public Video(String s){
        setSrc(s);
        getElement().setProperty("controls", true);
    }

    public String getSrc(){
        return get(srcDescriptor);
    }

    public void setSrc (String s){
        set(srcDescriptor, s);
    }
}
