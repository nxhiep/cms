BRANCH="$1"
if [[ -z "$BRANCH" ]]; then
	BRANCH=$(git branch --contains)
	BRANCH="${BRANCH//[* ]/}"
fi
MSG="$2"
FORCE="$3"
./compile-scss.sh
git status
git add .
if [[ -z "$MSG" ]]; then
    now=$(date +"%T - %D")
    git commit -m "edit $now"
else
    git commit -m "$MSG"
fi
if [[ -z "$BRANCH" ]]; then
	if [[ -z "$FORCE" ]]; then
		git push origin next
    else 
    	git push origin next "$FORCE"
    fi
else
	if [[ -z "$FORCE" ]]; then
	    git push origin "$BRANCH"
	else 
		git push origin "$BRANCH" "$FORCE"
	fi
fi
echo "Commit successful to $BRANCH!"