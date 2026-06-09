const API = 'http://localhost:8080';

const FOOD_MISSING = [
  ['coffee',     'café'],
  ['tea',        'thé'],
  ['beer',       'bière'],
  ['egg',        'œuf'],
  ['beef',       'bœuf'],
  ['pasta',      'pâtes'],
  ['cake',       'gâteau'],
  ['peach',      'pêche'],
  ['watermelon', 'pastèque'],
];

const BODY_MISSING = [
  ['head',     'tête'],
  ['eye',      'œil'],
  ['lip',      'lèvre'],
  ['shoulder', 'épaule'],
  ['heart',    'cœur'],
];

async function addCard(deckId, word, translation) {
  const res = await fetch(`${API}/decks/${deckId}/cards`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json; charset=utf-8' },
    body: JSON.stringify({ word, translation }),
  });
  return { status: res.status, word, translation };
}

async function run() {
  console.log('--- Food (deck 3) ---');
  for (const [w, t] of FOOD_MISSING) {
    const r = await addCard(3, w, t);
    console.log(`  ${r.status}  ${w} -> ${t}`);
  }
  console.log('--- Body parts (deck 4) ---');
  for (const [w, t] of BODY_MISSING) {
    const r = await addCard(4, w, t);
    console.log(`  ${r.status}  ${w} -> ${t}`);
  }
}

run().catch(e => { console.error(e); process.exit(1); });
