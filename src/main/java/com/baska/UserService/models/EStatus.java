package com.baska.UserService.models;

public enum EStatus {
    ACTIVE{
        @Override
        public String toString(){
            return "ACTIVE";
        }
    },
    DELETED{
        @Override
        public String toString(){
            return "DELETED";
        }
    }
}
