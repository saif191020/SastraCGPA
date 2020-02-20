package com.sba.sastracgpa;

public class GradeConverter {
    int convert(String s)
    {
        if(s.equals("S"))
        {
            return 10;
        }else if(s.equals("A+"))
        {
            return 9;
        }else if(s.equals("A"))
        {
            return 8;
        }else if(s.equals("B"))
        {
            return 7;
        }else if(s.equals("C"))
        {
            return 6;
        }else if(s.equals("D"))
        {
            return 5;
        }else
            return 2;
    }
}
