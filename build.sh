./gradlew clean

rm output/*
rm app/build/outputs/bundle/appReleaseRelease/*

./gradlew assembleAppTestRelease
cp app/build/outputs/apk/appTest/release/* output/

./gradlew release
./gradlew bundleAppReleaseRelease
cp app/build/outputs/bundle/appReleaseRelease/* output/
./gradlew remove_ads_release

