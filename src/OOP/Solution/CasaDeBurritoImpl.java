package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CasaDeBurritoImpl implements CasaDeBurrito,Cloneable {
    //Vars//
    private int mId;
    private String mName;
    private int mDistance;
    private Set<String> mMenu;
    private Map<Profesor,Integer> mRatings;
    private int mNumOfRatings;
    private int mTotalRatingScore;

    public Object clone(){
        return new CasaDeBurritoImpl(this.mId,this.mName,this.mDistance,this.mMenu);
    }

    public CasaDeBurritoImpl(int id, String name, int dist, Set<String> menu) {
        this.mId = id;
        this.mName = name;
        this.mDistance = dist;
        this.mMenu = menu;
        this.mNumOfRatings = 0;
        this.mTotalRatingScore = 0;
        mRatings = new HashMap<>();
    }

    @Override
    public int getId() {
        return this.mId;
    }

    @Override
    public String getName() {
        return this.mName;
    }

    @Override
    public int distance() {
        return this.mDistance;
    }

    @Override
    public boolean isRatedBy(Profesor p) {
        if(mRatings.containsKey(p)){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CasaDeBurrito rate(Profesor p, int r) throws RateRangeException {
        if(r > 5 || r < 0){
            throw new RateRangeException();
        }
        if (mRatings.containsKey(p)){
            this.mTotalRatingScore -= mRatings.get(p);
            this.mNumOfRatings -= 1;
        }
        mRatings.put(p,r);
        this.mTotalRatingScore += r;
        this.mNumOfRatings += 1;
        return this;
    }

    @Override
    public int numberOfRates() {
        return this.mNumOfRatings;
    }

    @Override
    public double averageRating() {
        if(mNumOfRatings == 0){
            return 0.0;
        }
        return (double)mTotalRatingScore/mNumOfRatings;
    }

    @Override
    public int compareTo(CasaDeBurrito o) {
        return this.getId() - o.getId();
    }

    @Override
    public boolean equals(Object b){
        if( b == null) return false;
        if(!(b instanceof CasaDeBurritoImpl)) return false;
        CasaDeBurritoImpl cdb = (CasaDeBurritoImpl) b;
        if(cdb.getId() == this.mId){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        String menuString = Stream.of(mMenu).map(x -> x + ", ").collect(Collectors.joining());
        menuString = menuString.substring(1,menuString.length() - 3);
        return "CasaDeBurrito: "+this.mName+".\n" +
                "Id: " +this.mId+".\n" +
                "Distance: "+this.mDistance+".\n" +
                "Menu: " + menuString + ".";
    }

    @Override
    public int hashCode(){
        int result = 19;
        result = result*23 + mId;
//        result = result*23 + mDistance;
//        result = result*23 + mName.hashCode();
//        result = result*23 + mMenu.hashCode();

        return result;
    }
}
