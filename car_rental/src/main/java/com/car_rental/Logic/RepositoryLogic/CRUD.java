package com.car_rental.Logic.RepositoryLogic;

/**
 This is a composite interface for the following functionalities:
    Create, Read, Update, Delete.
 */

public interface CRUD<T> extends Select<T>, Insert<T>, Update<T>, Delete<T> {}
