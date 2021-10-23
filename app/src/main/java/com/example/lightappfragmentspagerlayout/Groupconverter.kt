package com.example.lightappfragmentspagerlayout


class Groups_{
    var nameofgroup:String=""
    lateinit var listoflightsingroup:MutableList<String>
    var ISGROUP:Boolean=false
}



fun outputgroups(x: Array<String>):MutableList<Groups_>{
    var ml= mutableListOf<Groups_>()

    for (u in x)
    {
        if ("_%Master%_" in u){
            var g=Groups_()
            g.nameofgroup=u.replace("_%Master%_", "")
            g.listoflightsingroup=mutableListOf<String>()
            g.listoflightsingroup.add(g.nameofgroup)
            g.ISGROUP=true
            ml.add(g)

        }
        else {
            if ("_%Slave%_" in u) {

                ml.get(ml.size - 1).listoflightsingroup.add(u.replace("_%Slave%_", ""))
            }
            else
            {
                var g=Groups_()
                g.nameofgroup=u
                g.listoflightsingroup=mutableListOf<String>()
                g.listoflightsingroup.add(u)
                ml.add(g)

            }
        }
    }

    return ml
}

fun outputstrings(x: MutableList<Groups_>):Pair<MutableList<String>, MutableList<Int>>
{
    var listgroupsinstrings:MutableList<String> = mutableListOf()
    var grouptype:MutableList<Int> = mutableListOf()
    var level_:Int=0
    var nlight:Int=0
    //(0 and 0xff shl 24 or (0 and 0xff shl 16) or (0 and 0xff shl 8) or (0 and 0xff) )
    for (k in x)
    {
        for (z in 0..k.listoflightsingroup.size-1)
        {
            listgroupsinstrings.add(k.listoflightsingroup.get(z))
            if (k.ISGROUP && z==0)
            {
                level_=level_+1
                nlight=0
                var nn:Int =(1 and 0xff shl 16) or (nlight and 0xff shl 8) or (level_ and 0xff)
                grouptype.add(nn)
            }
            else
            {
                if (k.ISGROUP) {
                    nlight=nlight+1
                    var nn:Int = (nlight and 0xff shl 8) or (level_ and 0xff)
                    grouptype.add(nn)
                }
                else
                {
                    level_=level_+1
                    nlight=0
                    var nn:Int = (nlight and 0xff shl 8) or (level_ and 0xff)
                    grouptype.add(nn)
                }
            }
        }
    }
    return (Pair(listgroupsinstrings, grouptype))
}


fun makestrings(x:MutableList<Groups_>):Array<String>{

    var listmutable:MutableList<String> = mutableListOf()
    for (g in x)
        for (k in 0..g.listoflightsingroup.size-1)
        {
            if (g.ISGROUP==true)
            {
                if (k==0)
                {listmutable.add("_%Master%_" + g.listoflightsingroup.get(k))}
                else
                {listmutable.add("_%Slave%_" + g.listoflightsingroup.get(k))}
            }
            else
            {
                listmutable.add(g.listoflightsingroup.get(k))
            }
        }

    return listmutable.toTypedArray()
}

