package bola8.utilidades;

import java.util.AbstractList;
import java.util.List;

/**
 * Lista que contiene dos listas.
 * @author Jorge Berjano
 */
public class ListaDoble<E> extends AbstractList<E> {

    private List<E> lista1;
    private List<E> lista2;

    public ListaDoble(List<E> lista1, List<E> lista2) {
        this.lista1 = lista1;
        this.lista2 = lista2;
    }

    @Override
    public E get(int index) {
        if (index < lista1.size()) {
            return lista1.get(index);
        } else {
            return lista2.get(index - lista1.size());
        }
    }

    @Override
    public int size() {
        return lista1.size() + lista2.size();
    }
}
