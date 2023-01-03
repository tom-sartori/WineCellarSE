#!/bin/sh

# Source file copied : SamplePage

# if $1 and $2 are set
if [ -z "$1" ] || [ -z "$2" ]
then
    echo "Invalid arguments. "
    echo "\$1 = you should specify the target path you want. Example : page/partner. "
    echo "\$2 = you should specify the target you want. Example : Partner. "
    echo "You must set the first letter in upper case. "
    exit 1
fi


root="./src/main/java/ui/app/"
samplePath="page/sample"
sampleClass="SamplePage"
samplePackage="page.sample"
sampleFirstLower="samplePage"


targetFirstLower=$2
targetFirstLower="$(tr '[:upper:]' '[:lower:]' <<< ${targetFirstLower:0:1})${targetFirstLower:1}"

targetPackage=$(echo "$1" | sed -r 's/\//\./g')



sampleClassPath="$root""$samplePath/""$sampleClass"".java"
targetClassPath="$root""$1/$2.java"

sampleCssPath="$root""$samplePath/""$sampleClass"".css"
targetCssPath="$root""$1/$2.css"

sampleFxmlPath="$root""$samplePath/""$sampleClass"".fxml"
targetFxmlPath="$root""$1/$2.fxml"



mkdir -p "$root""$1"

cp $sampleClassPath $targetClassPath
cp $sampleCssPath $targetCssPath
cp $sampleFxmlPath $targetFxmlPath



sed -i "s/$sampleClass/$2/g" $targetClassPath
sed -i "s/$samplePackage/$targetPackage/g" $targetClassPath

sed -i "s/$sampleFirstLower/$targetFirstLower/g" $targetCssPath

sed -i "s#$samplePath#$1#g" $targetFxmlPath
sed -i "s/$samplePackage/$targetPackage/g" $targetFxmlPath
sed -i "s/$sampleClass/$2/g" $targetFxmlPath
sed -i "s/$sampleFirstLower/$targetFirstLower/g" $targetFxmlPath

echo "TODO : In the file ContentAreaPane.fxml, include the new fxml file. "
echo "TODO : In the file LeftMenuPane.fxml, duplicate a button and modify values.  "
echo "TODO : In the file LeftMenuPane.java, declare the new button (line 26).  "
