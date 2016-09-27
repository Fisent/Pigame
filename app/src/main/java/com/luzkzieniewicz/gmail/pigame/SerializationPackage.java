package com.luzkzieniewicz.gmail.pigame;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lukasz on 2016-09-27.
 */
public class SerializationPackage implements Serializable
{
    Date date;
    int hunger;
    int mud;
    int fun;
    int hoovdollars;

    SerializationPackage(int hunger, int mud, int fun, int hoovdollars, Date date)
    {
        this.hoovdollars = hoovdollars;
        this.date = date;
        this.mud = mud;
        this.fun = fun;
        this.hunger = hunger;
    }

    public int getHunger()
    {
        return hunger;
    }

    public int getMud()
    {
        return mud;
    }

    public int getFun()
    {
        return fun;
    }

    public Date getDate()
    {
        return date;
    }

}
