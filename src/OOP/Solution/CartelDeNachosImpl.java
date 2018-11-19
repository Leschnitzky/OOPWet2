package OOP.Solution;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CartelDeNachosImpl implements CartelDeNachos {
    public class Graph {
        private Map<Integer,List<Profesor>> adjacencyList;

        public Graph() {
            adjacencyList = new HashMap<>();
        }

        public Collection<Profesor> getNeighbors(Profesor p1){
            return adjacencyList.get(p1.getId());
        }

        public boolean contains(Profesor profesor){
            return adjacencyList.containsKey(profesor.getId());
        }

        public boolean containsEdge(Profesor p1,Profesor p2){
            return adjacencyList.get(p1.getId()).contains(p2);
        }

        public void addVertex(Profesor profesor){
            adjacencyList.put(profesor.getId(),new ArrayList<>());
        }

        public void addEdge(Profesor p1, Profesor p2){
            List<Profesor> list_p1 = adjacencyList.get(p1.getId());
            List<Profesor> list_p2 = adjacencyList.get(p2.getId());

            list_p1.add(p2);
            list_p2.add(p1);
        }

        private boolean bfsAux(Profesor prof, int level, Predicate<Profesor> pred, ArrayList<Integer> ids){
            if(level == 0){
                return pred.test(prof);
            }
            if(ids.contains(prof.getId())){
                return false;
            }
            ids.add(prof.getId());
            for (Profesor p : adjacencyList.get(prof.getId())){
                if(bfsAux(p,level - 1,pred,ids)){
                    return true;
                }
            }
            return false;
        }

        public boolean bfs(Profesor prof, int level, Predicate<Profesor> pred){
            return bfsAux(prof,level,pred,new ArrayList<>());
        }
    }


    private Map<Integer,ProfesorImpl> mProfesorMap;
    private Map<Integer,CasaDeBurritoImpl> mRestaurantsMap;

    private Graph mFriendshipGraph;

    public CartelDeNachosImpl(){
        mProfesorMap = new HashMap<>();
        mRestaurantsMap = new HashMap<>();

        mFriendshipGraph = new Graph();
    }

    @Override
    public Profesor joinCartel(int id, String name) throws Profesor.ProfesorAlreadyInSystemException {
        if(mProfesorMap.containsKey(id)){
            throw new Profesor.ProfesorAlreadyInSystemException();
        }
        Profesor new_prof = new ProfesorImpl(id,name);
        mProfesorMap.put(id, (ProfesorImpl) new_prof);
        mFriendshipGraph.addVertex(new_prof);
        return new_prof;
    }

    @Override
    public CasaDeBurrito addCasaDeBurrito(int id, String name, int dist, Set<String> menu) throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException {
        if(mRestaurantsMap.containsKey(id)){
            throw new CasaDeBurrito.CasaDeBurritoAlreadyInSystemException();
        }
        CasaDeBurrito cdb = new CasaDeBurritoImpl(id,name,dist,menu);
        mRestaurantsMap.put(id, (CasaDeBurritoImpl) cdb);
        return cdb;
    }

    @Override
    public Collection<Profesor> registeredProfesores() {
        ArrayList<Profesor> to_ret = new ArrayList<>();
        for(ProfesorImpl prof : mProfesorMap.values()){
            to_ret.add((Profesor) prof.clone());
        }
        return to_ret;
    }

    @Override
    public Collection<CasaDeBurrito> registeredCasasDeBurrito() {
        ArrayList<CasaDeBurrito> to_ret = new ArrayList<>();
        for(CasaDeBurritoImpl cdb : mRestaurantsMap.values()){
            to_ret.add((CasaDeBurrito) cdb.clone());
        }
        return to_ret;
    }


    @Override
    public Profesor getProfesor(int id) throws Profesor.ProfesorNotInSystemException {
        if(!mProfesorMap.containsKey(id)){
            throw new Profesor.ProfesorNotInSystemException();
        }
        return mProfesorMap.get(id);
    }

    @Override
    public CasaDeBurrito getCasaDeBurrito(int id) throws CasaDeBurrito.CasaDeBurritoNotInSystemException {
        if(!mRestaurantsMap.containsKey(id)){
            throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        }
        return mRestaurantsMap.get(id);
    }

    @Override
    public CartelDeNachos addConnection(Profesor p1, Profesor p2) throws Profesor.ProfesorNotInSystemException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        if(p1.equals(p2)){
            throw new Profesor.SameProfesorException();
        }
        if((!mProfesorMap.containsKey(p1.getId())) || (!mProfesorMap.containsKey(p2.getId()))){
            throw new Profesor.ProfesorNotInSystemException();
        }
        if(mFriendshipGraph.containsEdge(p1,p2)){
            throw new Profesor.ConnectionAlreadyExistsException();
        }
        mFriendshipGraph.addEdge(p1,p2);
        return this;
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(Profesor p) throws Profesor.ProfesorNotInSystemException {
        if(!mProfesorMap.containsKey(p.getId())){
            throw new Profesor.ProfesorNotInSystemException();
        }
        ArrayList<CasaDeBurrito> restaurants = new ArrayList<>();
        List<Profesor> sortedFriends = mFriendshipGraph.getNeighbors(p).stream().sorted().collect(Collectors.toList());
        for(Profesor friend : sortedFriends){
            restaurants.addAll(friend.filterAndSortFavorites((CasaDeBurrito x, CasaDeBurrito y) -> {
                if(x.averageRating() - y.averageRating() == 0){
                    if(x.distance() - y.distance() == 0){
                        return x.getId() - y.getId();
                    }
                    return x.distance() - y.distance();
                }
                if((y.averageRating() - x.averageRating()) < 0.0){
                    return -1;
                } else if((y.averageRating() - x.averageRating()) == 0.0){
                    return 0;
                } else{
                    return 1;
                }

            },(x) -> !restaurants.contains(x)));
        }
        return restaurants;
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByDist(Profesor p) throws Profesor.ProfesorNotInSystemException {
        if(!mProfesorMap.containsKey(p.getId())){
            throw new Profesor.ProfesorNotInSystemException();
        }
        ArrayList<CasaDeBurrito> restaurants = new ArrayList<>();
        List<Profesor> sortedFriends = mFriendshipGraph.getNeighbors(p).stream().sorted().collect(Collectors.toList());
        for(Profesor friend : sortedFriends){
            restaurants.addAll(friend.filterAndSortFavorites((x,y) -> {
                if(y.distance() - x.distance() == 0){
                    if(y.averageRating() - x.averageRating() == 0){
                        return x.getId() - y.getId();
                    }
                    if((y.averageRating() - x.averageRating()) < 0.0){
                        return -2;
                    } else if((y.averageRating() - x.averageRating()) == 0.0){
                        return 0;
                    } else{
                        return 1;
                    }
                }
                return x.distance() - y.distance();

            },(x) -> !restaurants.contains(x)));
        }
        return restaurants;
    }

    @Override
    public boolean getRecommendation(Profesor p, CasaDeBurrito c, int t) throws Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, ImpossibleConnectionException {
        if(!mProfesorMap.containsKey(p.getId())){
            throw new Profesor.ProfesorNotInSystemException();
        }
        if(!mRestaurantsMap.containsKey(c.getId())){
            throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        }
        if(t < 0){
            throw  new CartelDeNachos.ImpossibleConnectionException();
        }
        return mFriendshipGraph.bfs(p,t,(x)-> x.favorites().contains(c));
    }

    @Override
    public List<Integer> getMostPopularRestaurantsIds() {
        int score = 0,max_score = 0;
        Stream<Profesor> neighbors;
        Map<Integer,Integer> idsToScoreMap = new HashMap<>();
        for(CasaDeBurrito cdb : mRestaurantsMap.values()){
            score = 0;
            for(Profesor p: mProfesorMap.values()){
                neighbors = mFriendshipGraph.getNeighbors(p).stream();
                score += neighbors.filter((x)-> x.favorites().contains(cdb)).collect(Collectors.toList()).size();
            }
            if (score > max_score) {
                max_score = score;
            }
            idsToScoreMap.put(cdb.getId(),score);
        }
        int finalMax_score = max_score;
        return idsToScoreMap.entrySet().stream().
                sorted(Map.Entry.comparingByValue()).
                filter((x)-> x.getValue() == finalMax_score).
                map((x)-> x.getKey())
                .collect(Collectors.toList());
    }


    @Override
    public String toString(){
        String registeredProfessorsString = mProfesorMap.keySet().
                stream().sorted().
                map((x) -> { return Integer.toString(x);}).collect(Collectors.joining(", "));
        String registeredCasasString = mRestaurantsMap.keySet().stream()
                .sorted()
                .map((x) -> { return Integer.toString(x);})
                .collect(Collectors.joining(", "));
        String connectionsString = "";
        for(Profesor p: mProfesorMap.values().stream().sorted().collect(Collectors.toList())){
            connectionsString += Integer.toString(p.getId());
            connectionsString += " -> [";
            connectionsString += mFriendshipGraph.getNeighbors(p).stream().sorted().
                    map((x) -> Integer.toString(x.getId())).collect(Collectors.joining(", "));
            connectionsString += "].\n";
        }
        return "Registered profesores: "+registeredProfessorsString+".\n" +
                "Registered casas de burrito: "+registeredCasasString+".\n" +
                "Profesores:\n" +
                connectionsString+
                "End profesores.";
    }
}
