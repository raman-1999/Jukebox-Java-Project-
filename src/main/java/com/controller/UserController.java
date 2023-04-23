package com.controller;

import com.dao.DaoImpl;
public class UserController
{

    public static void main(String[] args) {

        System.out.println(" ");
        System.out.println(")-------------------------------------------------------------------------------(");
        System.out.println("|+-+-+-+-+-+-+-+-+-+-+-+-+-+-+- Welcome to Jukebox -+-+-+-+-+-+-+-+-+-+-+-+-+-+-|");
        System.out.println(")-------------------------------------------------------------------------------(");
        System.out.println("*********************************************************************************");
        DaoImpl obj = new DaoImpl();
        obj.displayMenu();
    }
}
