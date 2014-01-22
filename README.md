Guide
=========

1) Fork the repository in Github

2) Clone

```bash
git clone https://github.com/your username/guide.git
cd guide
git remote add shared https://github.com/escrichov/guide.git
```

3) Create android project with eclipse

New -> Other -> Android Project from Existing Code
Browse guide folder 


Quickstart
==========


MainActivity:
-------------

Basically the MainActivity implements the navigation drawer. The navigation drawer has different positions (like an Array) 0-X, all these positions react to a onClickListener. Easy. Therefore if an item is clicked, some function is being called. 
When starting the application, a object is created of the type LaunchMapActivity, which replaces the main layout part of the current view with our map. To change this object we can call a public method inside the LaunchMapActivity. 
But we can also create new Fragments on click of one of the icons the same way as the maps Fragment.
The Main Activity is a FragmentActivity thus being able to control Fragments.

LaunchMapActivity:
------------------

Includes all the functions assosiated to the map, how it should look like and what to implement. It extends Fragment because it's a mapFragment of type Fragment.

LaunchSettingsActivity:
-----------------------

A settings Fragment that should be (but right now isnt :() interchangeable with the LaunchMapActivity. Here all settings will be changeable.

