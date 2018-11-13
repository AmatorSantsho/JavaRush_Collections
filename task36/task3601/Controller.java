package com.javarush.task.task36.task3601;

import java.util.List;

/**
 * Created by 123 on 20.08.2017.
 */
public class Controller {


   private Model model=new Model();


    public List<String> onDataListShow() {
        return model.getStringDataList();
    }
    






}
