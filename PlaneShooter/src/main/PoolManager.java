package main;

import java.util.ArrayList;
import Entities.Interface.IPoolObject;

public class PoolManager
{
    private ArrayList<IPoolObject> poolObjects;

    public PoolManager()
    {
        poolObjects = new ArrayList<IPoolObject>();
    }
    public void AddPoolObject(IPoolObject obj)
    {
        poolObjects.add(obj);
    }
    public IPoolObject getReadyObject()
    {
        for(var poolObj: poolObjects)
        {
            if(poolObj.canReuse()){return poolObj;}
        }
        return null;
    }
    public ArrayList<IPoolObject> getPoolObjs()
    {
        return (ArrayList<IPoolObject>)poolObjects.clone();
    }
}