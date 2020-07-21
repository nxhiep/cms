ARRAYSTR=$(echo $(find $(pwd) -name *.scss))
IFS=' ' read -ra ADDR <<< "$ARRAYSTR"
for filePath in "${ADDR[@]}"; do
    echo "Compiling $filePath"
    sass $filePath $(echo "${filePath//.scss/.css}") -s compressed
done
echo "Compile successful!"