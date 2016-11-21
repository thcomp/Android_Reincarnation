# Android_Reincarnation
save and restore none parcelable instance

## Implementation
```
class SampleActivity extends Activity {
    @State YouClass mYouClass; // This will be automatically saved and restored

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReincarnationHelper.restore(this, savedInstanceState);
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ReincarnationHelper.save(this, outState);
    }
}
```

## Download
Gradle

```
repositories {
    maven { url 'http://raw.github.com/thcomp/Android_Reincarnation/master/repository' }
}
dependencies {
    compile 'jp.co.thcomp:reincarnation:0.2.0'
}
```

## Limitation
If you use this function for your class, **it must have default constructor**.
