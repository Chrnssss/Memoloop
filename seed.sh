#!/usr/bin/env bash
set -e
API=http://localhost:8080


create_deck() {
  curl -sS -X POST -H "Content-Type: application/json; charset=utf-8" \
    -d "{\"name\":\"$1\"}" "$API/decks" \
    | grep -o '"id":[0-9]*' | head -1 | grep -o '[0-9]*$'
}

add_card() {
  local deck_id="$1" word="$2" translation="$3"
  curl -sS -X POST -H "Content-Type: application/json; charset=utf-8" \
    -d "{\"word\":\"$word\",\"translation\":\"$translation\"}" \
    "$API/decks/$deck_id/cards" >/dev/null
}

food_cards=(
  "apple|pomme"
  "bread|pain"
  "cheese|fromage"
  "butter|beurre"
  "milk|lait"
  "water|eau"
  "coffee|café"
  "tea|thé"
  "wine|vin"
  "beer|bière"
  "egg|œuf"
  "chicken|poulet"
  "beef|bœuf"
  "pork|porc"
  "fish|poisson"
  "rice|riz"
  "pasta|pâtes"
  "potato|pomme de terre"
  "tomato|tomate"
  "carrot|carotte"
  "onion|oignon"
  "garlic|ail"
  "salt|sel"
  "pepper|poivre"
  "sugar|sucre"
  "honey|miel"
  "salad|salade"
  "soup|soupe"
  "cake|gâteau"
  "chocolate|chocolat"
  "cookie|biscuit"
  "strawberry|fraise"
  "banana|banane"
  "orange|orange"
  "grape|raisin"
  "lemon|citron"
  "peach|pêche"
  "pear|poire"
  "cherry|cerise"
  "watermelon|pastèque"
  "mushroom|champignon"
  "pineapple|ananas"
)

body_cards=(
  "head|tête"
  "hair|cheveux"
  "face|visage"
  "eye|œil"
  "ear|oreille"
  "nose|nez"
  "mouth|bouche"
  "lip|lèvre"
  "tooth|dent"
  "tongue|langue"
  "chin|menton"
  "neck|cou"
  "shoulder|épaule"
  "arm|bras"
  "elbow|coude"
  "wrist|poignet"
  "hand|main"
  "finger|doigt"
  "thumb|pouce"
  "nail|ongle"
  "chest|poitrine"
  "back|dos"
  "stomach|ventre"
  "waist|taille"
  "hip|hanche"
  "leg|jambe"
  "knee|genou"
  "ankle|cheville"
  "foot|pied"
  "toe|orteil"
  "heart|cœur"
  "brain|cerveau"
  "lung|poumon"
  "liver|foie"
  "bone|os"
  "skin|peau"
  "muscle|muscle"
  "blood|sang"
  "throat|gorge"
  "cheek|joue"
  "forehead|front"
  "eyebrow|sourcil"
)

food_id=$(create_deck "Food")
echo "Food deck id: $food_id"
for pair in "${food_cards[@]}"; do
  add_card "$food_id" "${pair%|*}" "${pair#*|}"
done

body_id=$(create_deck "Body parts")
echo "Body parts deck id: $body_id"
for pair in "${body_cards[@]}"; do
  add_card "$body_id" "${pair%|*}" "${pair#*|}"
done

echo "Done."
