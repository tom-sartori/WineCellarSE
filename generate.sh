#!/bin/sh

# Source file copied : Sample

# if $1 and $2 are set
if [ -z "$1" ]
then
    echo "Invalid arguments. "
    echo "\$1 = you should specify the target you want. Example : User. "
    echo "You must set the first letter in upper case. "
    exit 1
fi


root="./src/main/java/"
sampleEmplacement="sample"
sampleClass="Sample"
sampleLower="sample"
sampleUpper="SAMPLE"


targetLower=$1
targetLower="$(tr '[:upper:]' '[:lower:]' <<< ${targetLower})"
targetUpper=$1
targetUpper="$(tr '[:lower:]' '[:upper:]' <<< ${targetUpper})"



sampleControllerPath="$root""logic/controller/$sampleEmplacement/""$sampleClass""Controller.java"
targetControllerPath="$root""logic/controller/$targetLower/$1Controller.java"

sampleEntityPath="$root""persistence/entity/$sampleEmplacement/""$sampleClass"".java"
targetEntityPath="$root""persistence/entity/$targetLower/$1.java"

sampleDaoPath="$root""persistence/dao/$sampleEmplacement/""$sampleClass""Dao.java"
targetDaoPath="$root""persistence/dao/$targetLower/$1Dao.java"

sampleFacadePath="$root""facade/""$sampleClass""Facade.java"
targetFacadePath="$root""facade/$1Facade.java"



mkdir -p "$root""logic/controller/$targetLower"
mkdir -p "$root""persistence/entity/$targetLower"
mkdir -p "$root""persistence/dao/$targetLower"

cp $sampleControllerPath $targetControllerPath
cp $sampleEntityPath $targetEntityPath
cp $sampleDaoPath $targetDaoPath
cp $sampleFacadePath $targetFacadePath



gsed -i "s/$sampleClass/$1/g" $targetControllerPath
gsed -i "s/$sampleLower/$targetLower/g" $targetControllerPath

gsed -i "s/$sampleClass/$1/g" $targetEntityPath
gsed -i "s/$sampleLower/$targetLower/g" $targetEntityPath

gsed -i "s/$sampleClass/$1/g" $targetDaoPath
gsed -i "s/$sampleLower/$targetLower/g" $targetDaoPath
gsed -i "s/$sampleUpper/$targetUpper/g" $targetDaoPath

gsed -i "s/$sampleClass/$1/g" $targetFacadePath
gsed -i "s/$sampleLower/$targetLower/g" $targetFacadePath


echo "Update entity file with personalized fields. Generate getters, setters, equals, hashcode and compareTo. "
echo "Update CollectionNames with CollectionNames.$targetUpper. "
echo "Update function getSetOnUpdate() from $1Dao.java. "
echo "Be sure that you need every function in $1Facade.java. "
echo "Update Facade and FacadeInterface with functions from $1Facade.java. "
echo "TODO : Check every TODO which have been added if there still relevant. "
echo "TODO : Test file. "
