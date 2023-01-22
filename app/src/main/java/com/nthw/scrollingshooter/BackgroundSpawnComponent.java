package com.nthw.scrollingshooter;

class BackgroundSpawnComponent implements SpawnComponent{
    @Override
    public void spawn(Transform playerLTransform, Transform t){
// place the background in the top left corner
        t.setLocation(0f, 0f);
    }
}
