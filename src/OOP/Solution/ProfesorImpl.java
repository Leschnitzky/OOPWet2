package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProfesorImpl implements Profesor,Cloneable {
    private int mID;
    private String mName;

    private ArrayList<Profesor> mFriends;
    private ArrayList<CasaDeBurrito> mFavoriteRestaurants;

    public ProfesorImpl(int id, String name) {
        this.mID = id;
        this.mName = name;

        this.mFriends = new ArrayList<>();
        this.mFavoriteRestaurants = new ArrayList<>();

    }




    @Override
    public Object clone(){
        ProfesorImpl to_return = new ProfesorImpl(this.mID,this.mName);
        to_return.mFriends = this.mFriends;
        to_return.mFavoriteRestaurants = this.mFavoriteRestaurants;

        return to_return;
    }

    @Override
    public int getId() {
        return mID;
    }

    @Override
    public Profesor favorite(CasaDeBurrito c) throws UnratedFavoriteCasaDeBurritoException {
        if(!c.isRatedBy(this)){
            throw new UnratedFavoriteCasaDeBurritoException();
        }
        mFavoriteRestaurants.add((CasaDeBurritoImpl) c);
        return this;
    }

    @Override
    public Collection<CasaDeBurrito> favorites() {
        Collection<CasaDeBurrito> to_return  = new ArrayList<>();
        for(CasaDeBurrito cdb: this.mFavoriteRestaurants){
            CasaDeBurritoImpl to_ret = (CasaDeBurritoImpl) cdb;
            to_return.add((CasaDeBurritoImpl) to_ret.clone());
        }
        return to_return;
    }

    @Override
    public Profesor addFriend(Profesor p) throws SameProfesorException, ConnectionAlreadyExistsException {
        if( p.equals(this)){
            throw new SameProfesorException();
        }
        if (mFriends.contains(p)){
            throw new ConnectionAlreadyExistsException();
        }
        mFriends.add(p);
        return this;
    }

    @Override
    public Set<Profesor> getFriends() {
        Set<Profesor> friends  = new HashSet<>();
        for(Profesor prof: this.mFriends){
            ProfesorImpl to_add = (ProfesorImpl) prof;
            friends.add((ProfesorImpl) to_add.clone());
        }
        return friends;
    }

    @Override
    public Set<Profesor> filteredFriends(Predicate<Profesor> p) {
        Set<Profesor> set = new HashSet<>();
        for (Profesor profesor : mFriends) {
            if (p.test(profesor)) {
                set.add(profesor);
            }
        }
        return set;
    }

    @Override
    public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p) {
       return mFavoriteRestaurants.stream().
                filter(p).
                sorted(comp).collect(Collectors.toList());
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(int rLimit) {
        return filterAndSortFavorites(( x,  y) -> {
            if(x.averageRating() - y.averageRating() == 0){
                if(x.distance() - y.distance() == 0){
                    return x.getId() - y.getId();
                }
                return x.distance() - y.distance();
            }
            return (int)(y.averageRating() - x.averageRating());

        }, (x) -> x.averageRating() >= rLimit);
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByDist(int dLimit) {
        return filterAndSortFavorites((x,y) -> {
            if(y.distance() - x.distance() == 0){
                if(y.averageRating() - x.averageRating() == 0){
                    return x.getId() - y.getId();
                }
                return (int)(y.averageRating() - x.averageRating());
            }
            return x.distance() - y.distance();

        }, (x) -> x.distance() <= dLimit);
    }

    @Override
    public int compareTo(Profesor o) {
        return this.mID - o.getId();
    }

    @Override
    public boolean equals(Object b){
        if( b == null) return false;
        if(!(b instanceof Profesor)) return false;
        if (b.getClass() != this.getClass())
            return false;

        ProfesorImpl profesor = (ProfesorImpl) b;
        if(profesor.getId() == this.mID){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        String casaString = "";
        casaString = mFavoriteRestaurants.stream().map((x)-> x.getName()).sorted().collect(Collectors.joining(", "));
        return "Profesor: "+this.mName+".\n" +
                "Id: " +this.mID+".\n" +
                "Favorites: " + casaString + ".";
    }

    @Override
    public int hashCode(){
        int result = 17;
        result = result * 31 + this.mID;
//        result = 31*result + mName.hashCode();

        return result;
    }
    

}
