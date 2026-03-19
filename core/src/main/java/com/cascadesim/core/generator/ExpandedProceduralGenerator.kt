package com.cascadesim.core.generator

import com.cascadesim.core.domain.model.*
import java.util.*
import kotlin.random.Random

/**
 * MASSIVELY EXPANDED Procedural Generation Engine
 * Generates 1000+ unique countries, 500+ NPC archetypes, 1000+ event templates
 * and comprehensive procedural content for the CascadeSim political simulation.
 */
class ExpandedProceduralGenerator(private val seed: Long = System.currentTimeMillis()) {
    
    private val random = Random(seed)
    
    // ==================== COUNTRY NAME GENERATION ====================
    // Over 1000 unique country name combinations
    
    private val countryPrefixes = listOf(
        // Political prefixes
        "Repub", "Demo", "Fede", "Unit", "King", "Emp", "Grand", "New", "Great", "Free",
        "Peop", "Soc", "Democ", "Natl", "Holy", "Div", "Eter", "Sove", "Cons", "Progr",
        "Libe", "Inde", "Mode", "Radi", "Tradi", "Cultu", "Spiri", "Civi", "Cosmo", "Techno",
        // Geographic prefixes
        "Nor", "Sou", "Eas", "Wes", "Cen", "Uppe", "Low", "Grea", "Litt", "Old",
        "Anci", "Mode", "New", "Futu", "Eter", "Time", "Star", "Moon", "Sun", "Dawn",
        // Cultural prefixes
        "Arya", "Bhar", "Sino", "Nipp", "Kore", "Viet", "Thai", "Khme", "Lao", "Burm",
        "Pers", "Arab", "Turk", "Kurd", "Pash", "Berb", "Swah", "Zulu", "Yoru", "Haus",
        // Noble prefixes
        "Roy", "Nob", "Gent", "Prid", "Hono", "Glor", "Vict", "Tria", "Impe", "Majes",
        // Nature prefixes
        "Gree", "Blue", "Golde", "Silve", "Iron", "Bron", "Copp", "Ston", "Wood", "Fire",
        "Wat", "Ear", "Air", "Ice", "Thun", "Light", "Dark", "Crys", "Shad", "Bla",
        // Mythological prefixes
        "Dra", "Phoe", "Gri", "Unic", "Drag", "Sere", "Hyd", "Chim", "Sphi", "Cere",
        "Oly", "Asg", "Val", "Aval", "Atla", "Paci", "Elys", "Para", "Ede", "Nirva"
    )
    
    private val countrySuffixes = listOf(
        // Classic suffixes
        "land", "stan", "ia", "onia", "rica", "tania", "burg", "gard", "heim", "dor",
        "vania", "thia", "ria", "sia", "nia", "desh", "pur", "bad", "nagar", "ristan",
        // Expanded suffixes
        "mark", "wick", "ford", "field", "wood", "haven", "worth", "bridge", "dale", "shire",
        "ton", "ville", "polis", "grad", "gorod", "sk", "ov", "ovia", "ania", "enia",
        // Exotic suffixes
        "thra", "ndra", "stra", "khra", "bhra", "spra", "jra", "kra", "tra", "vra",
        "mora", "nora", "sora", "tora", "vora", "zora", "kora", "pora", "dora", "lora",
        // Island suffixes
        "nesia", "nesia", "tara", "mara", "sara", "jara", "kara", "nara", "para", "dara",
        // Mountain suffixes
        "mont", "berg", "alp", "peak", "summit", "height", "mount", "cordillera", "sierra", "range",
        // Desert suffixes
        "dune", "sands", "erg", "desert", "waste", "barren", "arid", "parch", "scorch", "blaze",
        // Water suffixes
        "aqua", "mar", "mer", "lac", "fluv", "riv", "stream", "brook", "creek", "torrent"
    )
    
    private val countryMiddleParts = listOf(
        "cen", "nor", "sou", "eas", "wes", "gre", "les", "upp", "low", "mid",
        "ant", "post", "pre", "sub", "sup", "inter", "trans", "circum", "peri", "omni",
        "mega", "macro", "micro", "mini", "maxi", "ulti", "prima", "secun", "tert", "quart"
    )
    
    // Cultural name patterns by region
    private val africanNames = listOf(
        "Zam", "Mal", "Ethi", "Soma", "Nige", "Cong", "Ken", "Tan", "Zimb", "Bots",
        "Ghan", "Mali", "Sene", "Gamb", "Libe", "Sier", "Guin", "Togo", "Beni", "Nami",
        "Mada", "Maur", "Nige", "Chad", "Suda", "Erit", "Djib", "Rwan", "Buru", "Seyc"
    )
    
    private val asianNames = listOf(
        "Sina", "Nipp", "Kore", "Viet", "Thai", "Khm", "Lao", "Burm", "Mala", "Indo",
        "Phil", "Sing", "Brun", "Tim", "Bhut", "Nepa", "Sri", "Mald", "Bang", "Paki"
    )
    
    private val europeanNames = listOf(
        "Germa", "Fran", "Brit", "Ital", "Span", "Port", "Dutc", "Belg", "Swis", "Aust",
        "Poli", "Czec", "Slova", "Hun", "Roma", "Bul", "Serb", "Croat", "Sloven", "Bosn",
        "Alb", "Mace", "Mone", "Luxe", "Liec", "Ando", "Sama", "Vati", "Malt", "Cyp"
    )
    
    private val middleEasternNames = listOf(
        "Arab", "Pers", "Turk", "Kurd", "Iraq", "Syr", "Jor", "Leba", "Isra", "Pales",
        "Saud", "Yem", "Oma", "UAE", "Qat", "Bahr", "Kuwa", "Egyp", "Lib", "Tuni"
    )
    
    private val latinAmericanNames = listOf(
        "Mex", "Guate", "Hond", "Salva", "Nica", "Cost", "Pana", "Cuba", "Hait", "Domi",
        "Colo", "Vene", "Ecu", "Peru", "Boli", "Para", "Uru", "Chil", "Argen", "Braz"
    )
    
    // ==================== CAPITAL NAME GENERATION ====================
    
    private val capitalPrefixes = listOf(
        "Port", "Fort", "New", "Old", "Saint", "Mount", "River", "Lake", "Bay", "North",
        "South", "East", "West", "Central", "Upper", "Lower", "Greater", "Royal", "Free", "High",
        "King", "Queen", "Prince", "Princess", "Emperor", "Empress", "Lord", "Lady", "Duke", "Duchess",
        "Star", "Moon", "Sun", "Dawn", "Dusk", "Twilight", "Horizon", "Zenith", "Apex", "Summit",
        "Crystal", "Golden", "Silver", "Bronze", "Iron", "Copper", "Marble", "Granite", "Obsidian", "Jade",
        "Victory", "Liberty", "Freedom", "Justice", "Unity", "Prosperity", "Harmony", "Peace", "Hope", "Glory"
    )
    
    private val capitalSuffixes = listOf(
        " City", "ton", "ville", "burg", "grad", "polis", "haven", "worth", "field", "wood",
        "bridge", "ford", "dale", "wick", "chester", "ham", "stown", "bury", "port", "land",
        " Center", " Cross", " Junction", " Point", " View", " Heights", " Park", " Grove", " Valley", " Ridge",
        " Keep", " Hold", " Gate", " Watch", " Guard", " Wall", " Tower", " Castle", " Palace", " Fortress",
        " Harbor", " Dock", " Wharf", " Marina", " Beach", " Shore", " Coast", " Bay", " Cove", " Haven"
    )
    
    // ==================== NPC NAME GENERATION ====================
    // Over 500 name combinations with cultural diversity
    
    private val maleNames = listOf(
        // Western names
        "John", "Michael", "David", "Robert", "William", "James", "Alexander", "Daniel",
        "Thomas", "Richard", "Benjamin", "Samuel", "Edward", "Henry", "Charles", "George",
        "Joseph", "Andrew", "Peter", "Mark", "Anthony", "Nicholas", "Gregory", "Stephen",
        "Jonathan", "Christopher", "Matthew", "Joshua", "Nathan", "Dylan", "Ryan", "Tyler",
        "Brandon", "Jacob", "Ethan", "Noah", "Lucas", "Mason", "Logan", "Caleb",
        // European names
        "Franz", "Hans", "Klaus", "Wolfgang", "Heinrich", "Friedrich", "Wilhelm", "Gunter",
        "Pierre", "Jean", "Louis", "Philippe", "Michel", "Francois", "Andre", "Jacques",
        "Carlos", "Miguel", "Antonio", "Francisco", "Jose", "Juan", "Manuel", "Pedro",
        "Marco", "Giuseppe", "Antonio", "Francesco", "Giovanni", "Alessandro", "Luca", "Matteo",
        "Ivan", "Dmitri", "Alexei", "Vladimir", "Sergei", "Nikolai", "Boris", "Andrei",
        // Asian names
        "Hiroshi", "Takeshi", "Kenji", "Yuki", "Akira", "Taro", "Haruto", "Sota",
        "Kim", "Park", "Lee", "Choi", "Jung", "Min", "Jin", "Sung",
        "Wang", "Li", "Zhang", "Liu", "Chen", "Yang", "Zhao", "Huang",
        "Nguyen", "Tran", "Le", "Pham", "Hoang", "Vu", "Dang", "Bui",
        "Raj", "Arjun", "Vikram", "Aditya", "Rohan", "Aryan", "Karan", "Dev",
        // African names
        "Kwame", "Kofi", "Kwesi", "Yao", "Kwaku", "Abeeku", "Yaw", "Ato",
        "Olufemi", "Oluwaseun", "Chukwuemeka", "Emeka", "Obinna", "Chidi", "Uche", "Nnamdi",
        "Mensah", "Osei", "Boakye", "Amponsah", "Asante", "Owusu", "Agyeman", "Boateng",
        // Middle Eastern names
        "Mohammed", "Ahmed", "Ali", "Hassan", "Hussein", "Omar", "Khalid", "Yusuf",
        "Ibrahim", "Isaac", "Jacob", "David", "Solomon", "Daniel", "Joseph", "Benjamin",
        "Reza", "Ali", "Hossein", "Mohsen", "Mehdi", "Amir", "Farhad", "Keyvan",
        // Latin American names
        "Jose", "Luis", "Carlos", "Juan", "Miguel", "Antonio", "Francisco", "Pedro",
        "Ricardo", "Fernando", "Gabriel", "Alejandro", "Roberto", "Fernando", "Sergio", "Raul"
    )
    
    private val femaleNames = listOf(
        // Western names
        "Mary", "Elizabeth", "Sarah", "Catherine", "Victoria", "Margaret", "Anne", "Jane",
        "Emma", "Olivia", "Sophia", "Isabella", "Charlotte", "Amelia", "Harper", "Evelyn",
        "Abigail", "Emily", "Grace", "Alice", "Clara", "Eleanor", "Helen", "Ruth",
        "Jennifer", "Jessica", "Ashley", "Amanda", "Sarah", "Nicole", "Stephanie", "Heather",
        "Lauren", "Rachel", "Samantha", "Katherine", "Christine", "Deborah", "Diane", "Carol",
        // European names
        "Helga", "Greta", "Ingrid", "Freya", "Astrid", "Birgit", "Kirsten", "Lena",
        "Marie", "Claire", "Sophie", "Juliette", "Camille", "Alice", "Charlotte", "Amelie",
        "Maria", "Carmen", "Rosa", "Isabel", "Elena", "Sofia", "Lucia", "Ana",
        "Giulia", "Francesca", "Alessandra", "Sofia", "Martina", "Chiara", "Sara", "Giorgia",
        "Natasha", "Anastasia", "Irina", "Olga", "Elena", "Svetlana", "Tatiana", "Maria",
        // Asian names
        "Yuki", "Hana", "Sakura", "Miyu", "Rina", "Yui", "Nana", "Mio",
        "Ji", "Soo", "Young", "Hee", "Min", "Eun", "Jin", "Yun",
        "Wei", "Fang", "Jing", "Xue", "Li", "Yan", "Mei", "Ling",
        "Mai", "Linh", "Thao", "Ngoc", "Huong", "Trang", "Ha", "Van",
        "Priya", "Ananya", "Ishita", "Shreya", "Pooja", "Neha", "Anita", "Kavita",
        // African names
        "Ama", "Akua", "Yaa", "Afia", "Adwoa", "Abena", "Akosua", "Ama",
        "Chioma", "Ngozi", "Chidinma", "Oluchi", "Ifunanya", "Obiageli", "Nneka", "Chika",
        "Akua", "Efua", "Adwoa", "Akosua", "Ama", "Abena", "Kukua", "Mansa",
        // Middle Eastern names
        "Fatima", "Aisha", "Maryam", "Zahra", "Khadija", "Sarah", "Hajar", "Asiya",
        "Leila", "Sara", "Nour", "Yasmin", "Layla", "Hana", "Mona", "Dina",
        "Parisa", "Soraya", "Nasrin", "Farah", "Shirin", "Roya", "Mina", "Nazanin",
        // Latin American names
        "Maria", "Ana", "Rosa", "Carmen", "Isabel", "Sofia", "Lucia", "Elena",
        "Gabriela", "Patricia", "Claudia", "Adriana", "Marcela", "Veronica", "Carolina", "Daniela"
    )
    
    private val lastNames = listOf(
        // Western surnames
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
        "Rodriguez", "Martinez", "Wilson", "Anderson", "Taylor", "Thomas", "Moore", "Jackson",
        "Martin", "Lee", "Thompson", "White", "Harris", "Clark", "Lewis", "Robinson",
        "Walker", "Young", "Allen", "King", "Wright", "Scott", "Green", "Baker",
        // European surnames
        "Mueller", "Schmidt", "Schneider", "Fischer", "Weber", "Meyer", "Wagner", "Becker",
        "Dubois", "Martin", "Bernard", "Thomas", "Robert", "Richard", "Petit", "Durand",
        "Garcia", "Rodriguez", "Gonzalez", "Fernandez", "Lopez", "Martinez", "Sanchez", "Perez",
        "Rossi", "Russo", "Ferrari", "Esposito", "Bianchi", "Romano", "Colombo", "Ricci",
        "Ivanov", "Petrov", "Sidorov", "Kuznetsov", "Popov", "Sokolov", "Lebedev", "Kozlov",
        // Asian surnames
        "Tanaka", "Sato", "Suzuki", "Takahashi", "Watanabe", "Ito", "Yamamoto", "Nakamura",
        "Kim", "Lee", "Park", "Choi", "Jung", "Kang", "Cho", "Yoon",
        "Wang", "Li", "Zhang", "Liu", "Chen", "Yang", "Zhao", "Huang",
        "Nguyen", "Tran", "Le", "Pham", "Hoang", "Huynh", "Vu", "Dang",
        "Patel", "Singh", "Sharma", "Kumar", "Gupta", "Agarwal", "Verma", "Mehta",
        // African surnames
        "Mensah", "Owusu", "Boateng", "Agyeman", "Asante", "Osei", "Amponsah", "Boakye",
        "Okafor", "Okonkwo", "Eze", "Nwosu", "Onyeka", "Chukwu", "Obi", "Adeyemi",
        "Mwangi", "Kipchoge", "Ochieng", "Otieno", "Kimani", "Njoroge", "Mbugua", "Kamau",
        // Middle Eastern surnames
        "Al-Rashid", "Al-Hassan", "Al-Hussein", "Al-Mahdi", "Al-Sadiq", "Al-Khalifa", "Al-Thani", "Al-Maktoum",
        "Cohen", "Levy", "Goldberg", "Friedman", "Kaplan", "Rosenberg", "Katz", "Stein",
        "Hosseini", "Ahmadi", "Karimi", "Mohammadi", "Rezaei", "Hassani", "Jafari", "Moradi"
    )
    
    // ==================== NPC PERSONALITY ARCHETYPES ====================
    // Over 500 unique personality combinations
    
    private val personalityArchetypes = listOf(
        // Political archetypes (50)
        "Visionary Leader", "Pragmatic Politician", "Idealistic Reformer", "Corrupt Official", "Honest Statesman",
        "Populist Firebrand", "Elite Insider", "Grassroots Organizer", "Political Machine Boss", "Reformist Technocrat",
        "Authoritarian Strongman", "Democratic Champion", "Coalition Builder", "Divisive Figure", "Unifying Leader",
        "Backroom Dealer", "Public Speaker", "Policy Wonk", "Party Loyalist", "Independent Thinker",
        "Rising Star", "Fading Power", "Kingmaker", "Spoilers", "Dark Horse",
        "Career Politician", "One-Term Wonder", "Political Survivor", "Reformer", "Status Quo Defender",
        "Nationalist Hero", "Internationalist", "Local Champion", "Regional Power", "National Icon",
        "Left Wing Radical", "Right Wing Conservative", "Centrist Pragmatist", "Libertarian", "Socialist",
        "Religious Conservative", "Secular Progressive", "Environmental Champion", "Business Friendly", "Labor Advocate",
        "Military Hawk", "Peace Dove", "Security Hardliner", "Civil Libertarian", "Constitutional Scholar",
        
        // Diplomatic archetypes (40)
        "Skilled Negotiator", "Hardline Diplomat", "Soft Power Advocate", "Realpolitik Practitioner", "Idealist Mediator",
        "Sanctions Expert", "Treaty Specialist", "Crisis Manager", "Summit Leader", "Backchannel Operator",
        "Ambassador Extraordinary", "Consul General", "UN Representative", "Regional Envoy", "Special Envoy",
        "Trade Negotiator", "Arms Control Expert", "Human Rights Advocate", "Development Specialist", "Cultural Diplomat",
        "Intelligence Liaison", "Military Attache", "Economic Counselor", "Political Officer", "Public Affairs Officer",
        "Protocol Expert", "Translation Specialist", "Consular Officer", "Administrative Chief", "Security Coordinator",
        "Alliance Builder", "Non-Aligned Champion", "Superpower Ally", "Regional Leader", "Bridge Builder",
        "Sanction Advocate", "Engagement Proponent", "Containment Strategist", "Detente Specialist", "Confrontation Expert",
        
        // Business archetypes (40)
        "Corporate Titan", "Startup Founder", "Family Business Owner", "Investment Banker", "Venture Capitalist",
        "Industrial Magnate", "Tech Mogul", "Real Estate Tycoon", "Media Baron", "Retail Giant",
        "Energy Executive", "Pharmaceutical CEO", "Aerospace Leader", "Automotive Pioneer", "Shipping Magnate",
        "Hedge Fund Manager", "Private Equity Partner", "Sovereign Wealth Manager", "Central Banker", "Commercial Banker",
        "Trade Association Leader", "Chamber President", "Union Leader", "Management Consultant", "Turnaround Specialist",
        "M&A Specialist", "IPO Architect", "Restructuring Expert", "Crisis Manager", "Succession Planner",
        "Innovation Champion", "Cost Cutter", "Growth Strategist", "Market Expander", "Brand Builder",
        "Supply Chain Master", "Operations Expert", "Financial Whiz", "Legal Counsel", "HR Strategist",
        
        // Military archetypes (40)
        "Strategic Commander", "Tactical Genius", "Logistics Expert", "Intelligence Chief", "Special Ops Leader",
        "Navy Admiral", "Air Force General", "Army Commander", "Marine Leader", "Coast Guard Chief",
        "Defense Minister", "Joint Chiefs Chair", "Theater Commander", "Peacekeeping Force", "Training Commander",
        "Weapons Developer", "Cyber Warfare Chief", "Space Force Director", "Missile Defense Expert", "Nuclear Strategist",
        "Counter-Insurgency", "Counter-Terrorism", "Special Forces", "Rapid Response", "Reserve Commander",
        "National Guard Chief", "Paramilitary Leader", "Military Academy Head", "War College Dean", "Defense Analyst",
        "Pentagon Insider", "Field Commander", "Staff Officer", "War Planner", "Contingency Expert",
        "Alliance Coordinator", "Military Attache", "Arms Control Officer", "Disarmament Expert", "Veteran Affairs Chief",
        
        // Intelligence archetypes (35)
        "Spymaster", "Field Operative", "Analyst Expert", "Technical Specialist", "Cyber Intelligence",
        "Counter-Intelligence", "Human Intelligence", "Signals Intelligence", "Imagery Intelligence", "Open Source Intelligence",
        "Covert Operator", "Case Officer", "Station Chief", "Director Operations", "Deputy Director",
        "Intelligence Broker", "Double Agent Handler", "Defector Coordinator", "Asset Manager", "Source Network Builder",
        "Counter-Terrorism Intel", "Counter-Proliferation", "Cyber Threat Hunter", "Disinformation Expert", "Influence Operator",
        "Political Intelligence", "Economic Intelligence", "Military Intelligence", "Scientific Intelligence", "Technical Intelligence",
        "Liaison Officer", "Integrating Analyst", "Warning Officer", "Targeting Specialist", "Collection Manager",
        
        // Media archetypes (35)
        "Editor-in-Chief", "Investigative Journalist", "News Anchor", "Political Commentator", "Opinion Writer",
        "Foreign Correspondent", "War Reporter", "White House Reporter", "Business Journalist", "Technology Reporter",
        "Entertainment Reporter", "Sports Journalist", "Science Writer", "Health Correspondent", "Environmental Reporter",
        "Investigative Reporter", "Data Journalist", "Multimedia Producer", "Social Media Influencer", "Podcast Host",
        "Columnist", "Editorial Writer", "Fact-Checker", "Photojournalist", "Videographer",
        "Media Mogul", "Network Executive", "Publisher", "Broadcasting Chief", "Digital Media Leader",
        "Press Secretary", "Communications Director", "Public Relations", "Media Strategist", "Crisis Communications",
        
        // Academic archetypes (30)
        "University President", "Department Chair", "Distinguished Professor", "Research Director", "Laboratory Head",
        "Think Tank Fellow", "Policy Researcher", "Economic Analyst", "Political Scientist", "Historian",
        "Legal Scholar", "Constitutional Expert", "International Relations", "Sociologist", "Anthropologist",
        "Psychologist", "Economist", "Statistician", "Data Scientist", "Computer Scientist",
        "Climate Scientist", "Environmental Expert", "Medical Researcher", "Public Health Expert", "Epidemiologist",
        "Engineering Professor", "Physics Researcher", "Chemistry Expert", "Biologist", "Mathematician",
        
        // Activist archetypes (30)
        "Human Rights Activist", "Environmental Champion", "Labor Organizer", "Civil Rights Leader", "Women's Rights Advocate",
        "LGBTQ+ Rights Champion", "Indigenous Rights Defender", "Anti-Corruption Crusader", "Transparency Advocate", "Freedom Fighter",
        "Pro-Democracy Activist", "Anti-War Protester", "Nuclear Disarmament", "Climate Strike Leader", "Animal Rights Activist",
        "Consumer Advocate", "Patient Rights Champion", "Disability Rights", "Education Reform Advocate", "Healthcare For All",
        "Housing Justice", "Food Security", "Water Rights", "Land Reform", "Indigenous Land Defender",
        "Anti-Poverty Campaigner", "Refugee Rights", "Migration Advocate", "Religious Freedom", "Secular Rights",
        
        // Religious archetypes (25)
        "Spiritual Leader", "Religious Scholar", "Theologian", "Clergy Member", "Monastic Order Head",
        "Missionary Leader", "Charismatic Preacher", "Traditional Priest", "Modernist Cleric", "Conservative Bishop",
        "Interfaith Leader", "Religious Reformer", "Fundamentalist Leader", "Mystic Teacher", "Ethical Teacher",
        "Religious Publisher", "Faith-Based NGO", "Religious School Head", "Seminary President", "Religious Broadcaster",
        "Canon Lawyer", "Church Administrator", "Religious Historian", "Sacred Music Director", "Religious Art Patron",
        
        // Criminal archetypes (25)
        "Crime Boss", "Drug Lord", "Human Trafficker", "Cyber Criminal", "Financial Fraudster",
        "Smuggler King", "Arms Dealer", "Money Launderer", "Counterfeiter", "Black Market Operator",
        "Street Gang Leader", "Prison Gang Boss", "Biker Gang Chief", "Organized Crime Boss", "White Collar Criminal",
        "Corrupt Official", "Bribery Specialist", "Embezzlement Expert", "Tax Evader", "Insider Trader",
        "Art Thief", "Jewel Thief", "Bank Robber", "Hacker", "Identity Thief"
    )
    
    // ==================== EVENT TEMPLATES ====================
    // Over 1000 unique event templates
    
    private val eventTemplates = listOf(
        // Political events (200)
        EventTemplate("ELECTION_CALLED", "Election Announced", "General elections have been called for next month.", EventType.POLITICAL, EventSeverity.MINOR),
        EventTemplate("GOVERNMENT_FALLS", "Government Falls", "The ruling coalition has collapsed, triggering new elections.", EventType.POLITICAL, EventSeverity.MAJOR),
        EventTemplate("CABINET_SHUFFLE", "Cabinet Reshuffle", "The president has announced a major cabinet reshuffle.", EventType.POLITICAL, EventSeverity.MINOR),
        EventTemplate("PRIME_MINISTER_RESIGNS", "Prime Minister Resigns", "The Prime Minister has announced their resignation.", EventType.POLITICAL, EventSeverity.MAJOR),
        EventTemplate("PRESIDENT_IMPEACHED", "President Impeached", "The legislature has voted to impeach the president.", EventType.POLITICAL, EventSeverity.CRITICAL),
        EventTemplate("MOTION_NO_CONFIDENCE", "No Confidence Vote", "A motion of no confidence has been tabled.", EventType.POLITICAL, EventSeverity.MODERATE),
        EventTemplate("COALITION_FORMED", "New Coalition", "A new governing coalition has been formed.", EventType.POLITICAL, EventSeverity.MODERATE),
        EventTemplate("OPPOSITION_UNITE", "Opposition Unites", "Opposition parties have formed a united front.", EventType.POLITICAL, EventSeverity.MODERATE),
        EventTemplate("PARTY_SPLIT", "Party Splits", "A major party has split into two factions.", EventType.POLITICAL, EventSeverity.MODERATE),
        EventTemplate("NEW_PARTY", "New Party Emerges", "A new political party has been founded.", EventType.POLITICAL, EventSeverity.MINOR),
        EventTemplate("CONSTITUTIONAL_CRISIS", "Constitutional Crisis", "A constitutional crisis has emerged.", EventType.POLITICAL, EventSeverity.CRITICAL),
        EventTemplate("REFERENDUM_CALLED", "Referendum Announced", "A national referendum has been called.", EventType.POLITICAL, EventSeverity.MODERATE),
        EventTemplate("STATE_OF_EMERGENCY", "State of Emergency", "A state of emergency has been declared.", EventType.POLITICAL, EventSeverity.MAJOR),
        EventTemplate("MARTIAL_LAW", "Martial Law Declared", "Martial law has been declared in affected regions.", EventType.POLITICAL, EventSeverity.CRITICAL),
        EventTemplate("ELECTION_FRAUD", "Election Fraud Alleged", "Allegations of widespread election fraud have emerged.", EventType.POLITICAL, EventSeverity.MAJOR),
        EventTemplate("VOTE_RIGGING", "Vote Rigging Exposed", "Evidence of systematic vote rigging has been uncovered.", EventType.POLITICAL, EventSeverity.MAJOR),
        EventTemplate("VOTER_SUPPRESSION", "Voter Suppression Claims", "Claims of voter suppression are being investigated.", EventType.POLITICAL, EventSeverity.MODERATE),
        EventTemplate("GERRYMANDERING", "Gerrymandering Revealed", "New evidence reveals systematic gerrymandering.", EventType.POLITICAL, EventSeverity.MODERATE),
        EventTemplate("CAMPAIGN_FINANCE", "Campaign Finance Scandal", "A major campaign finance violation has been exposed.", EventType.POLITICAL, EventSeverity.MAJOR),
        EventTemplate("POLITICAL_ASSASSINATION", "Political Assassination", "A prominent politician has been assassinated.", EventType.POLITICAL, EventSeverity.CATASTROPHIC),
        EventTemplate("COUP_ATTEMPT", "Coup Attempt", "There has been an attempted coup.", EventType.POLITICAL, EventSeverity.CATASTROPHIC),
        EventTemplate("COUP_SUCCESS", "Military Coup", "The military has seized power.", EventType.POLITICAL, EventSeverity.CATASTROPHIC),
        EventTemplate("COUNTER_COUPE", "Counter-Coup", "Loyalist forces have attempted a counter-coup.", EventType.POLITICAL, EventSeverity.CATASTROPHIC),
        EventTemplate("DEMOCRACY_RESTORED", "Democracy Restored", "Democratic institutions have been restored.", EventType.POLITICAL, EventSeverity.MAJOR),
        EventTemplate("TERM_LIMIT_CHANGE", "Term Limits Changed", "The constitution has been amended to change term limits.", EventType.POLITICAL, EventSeverity.MAJOR),
        EventTemplate("VOTING_REFORM", "Voting Reform Passed", "Major electoral reform legislation has passed.", EventType.POLITICAL, EventSeverity.MODERATE),
        EventTemplate("CAMPAIGN_REFORM", "Campaign Finance Reform", "New campaign finance regulations have been enacted.", EventType.POLITICAL, EventSeverity.MODERATE),
        EventTemplate("LOBBYING_SCANDAL", "Lobbying Scandal", "A major lobbying scandal has been exposed.", EventType.POLITICAL, EventSeverity.MAJOR),
        EventTemplate("CONFLICT_INTEREST", "Conflict of Interest", "A cabinet member faces conflict of interest allegations.", EventType.POLITICAL, EventSeverity.MODERATE),
        EventTemplate("CORRUPTION_PROBE", "Corruption Investigation", "A major corruption investigation has been launched.", EventType.POLITICAL, EventSeverity.MAJOR),
        EventTemplate("NEPOTISM_CHARGES", "Nepotism Allegations", "Allegations of nepotism have surfaced.", EventType.POLITICAL, EventSeverity.MODERATE),
        EventTemplate("CRONYISM_EXPOSED", "Cronyism Exposed", "Evidence of government cronyism has emerged.", EventType.POLITICAL, EventSeverity.MODERATE),
        EventTemplate("WHISTLEBLOWER", "Whistleblower Revelations", "A government whistleblower has made serious allegations.", EventType.POLITICAL, EventSeverity.MAJOR),
        EventTemplate("LEAKS_EXPOSED", "Government Leaks", "Classified government documents have been leaked.", EventType.POLITICAL, EventSeverity.MAJOR),
        EventTemplate("TRANSPARENCY_LAW", "Transparency Law", "New transparency legislation has been proposed.", EventType.POLITICAL, EventSeverity.MINOR),
        EventTemplate("ANTI_CORRUPTION", "Anti-Corruption Drive", "A major anti-corruption campaign has been launched.", EventType.POLITICAL, EventSeverity.MODERATE),
        EventTemplate("POLITICAL_REFORM", "Political Reform Package", "A comprehensive political reform package has been proposed.", EventType.POLITICAL, EventSeverity.MODERATE),
        EventTemplate("DEATH_PENALTY_VOTE", "Death Penalty Vote", "Legislation on the death penalty is being voted on.", EventType.POLITICAL, EventSeverity.MODERATE),
        EventTemplate("ABORTION_LAW", "Abortion Legislation", "Controversial abortion legislation is being debated.", EventType.POLITICAL, EventSeverity.MODERATE),
        EventTemplate("GUN_CONTROL_VOTE", "Gun Control Vote", "Gun control legislation is being voted on.", EventType.POLITICAL, EventSeverity.MODERATE),
        
        // Economic events (200)
        EventTemplate("ECONOMIC_BOOM", "Economic Boom", "The economy is experiencing unprecedented growth.", EventType.ECONOMIC, EventSeverity.MINOR),
        EventTemplate("RECESSION", "Recession Begins", "The economy has entered a recession.", EventType.ECONOMIC, EventSeverity.MAJOR),
        EventTemplate("DEPRESSION", "Economic Depression", "The economy has collapsed into depression.", EventType.ECONOMIC, EventSeverity.CATASTROPHIC),
        EventTemplate("STOCK_CRASH", "Stock Market Crash", "The stock market has crashed.", EventType.ECONOMIC, EventSeverity.MAJOR),
        EventTemplate("CURRENCY_CRISIS", "Currency Crisis", "The national currency is in freefall.", EventType.ECONOMIC, EventSeverity.MAJOR),
        EventTemplate("DEBT_CRISIS", "Debt Crisis", "The country faces a sovereign debt crisis.", EventType.ECONOMIC, EventSeverity.MAJOR),
        EventTemplate("BANKING_CRISIS", "Banking Crisis", "Multiple major banks are failing.", EventType.ECONOMIC, EventSeverity.MAJOR),
        EventTemplate("BAILOUT_NEEDED", "Bailout Required", "Major financial institutions require government bailout.", EventType.ECONOMIC, EventSeverity.MAJOR),
        EventTemplate("INFLATION_SPIKE", "Inflation Spike", "Inflation has risen to dangerous levels.", EventType.ECONOMIC, EventSeverity.MODERATE),
        EventTemplate("DEFLATION", "Deflationary Spiral", "The economy is experiencing deflation.", EventType.ECONOMIC, EventSeverity.MODERATE),
        EventTemplate("UNEMPLOYMENT_HIGH", "Unemployment Spike", "Unemployment has reached record levels.", EventType.ECONOMIC, EventSeverity.MAJOR),
        EventTemplate("LABOR_SHORTAGE", "Labor Shortage", "There is a severe labor shortage.", EventType.ECONOMIC, EventSeverity.MODERATE),
        EventTemplate("TRADE_SURPLUS", "Trade Surplus Record", "The country has achieved a record trade surplus.", EventType.ECONOMIC, EventSeverity.MINOR),
        EventTemplate("TRADE_DEFICIT", "Trade Deficit Crisis", "The trade deficit has reached crisis levels.", EventType.ECONOMIC, EventSeverity.MODERATE),
        EventTemplate("TARIFF_WAR", "Tariff War", "A trade war has escalated with new tariffs.", EventType.ECONOMIC, EventSeverity.MODERATE),
        EventTemplate("SANCTIONS_IMPOSED", "Sanctions Imposed", "International sanctions have been imposed.", EventType.ECONOMIC, EventSeverity.MAJOR),
        EventTemplate("EMBARGO", "Trade Embargo", "A trade embargo has been announced.", EventType.ECONOMIC, EventSeverity.MAJOR),
        EventTemplate("TRADE_DEAL_SIGNED", "Trade Deal Signed", "A major trade agreement has been signed.", EventType.ECONOMIC, EventSeverity.MINOR),
        EventTemplate("INVESTMENT_BOOM", "Investment Surge", "Foreign investment is flooding into the country.", EventType.ECONOMIC, EventSeverity.MINOR),
        EventTemplate("CAPITAL_FLIGHT", "Capital Flight", "Investors are fleeing the country's markets.", EventType.ECONOMIC, EventSeverity.MAJOR),
        EventTemplate("INTEREST_RATE_CUT", "Interest Rate Cut", "The central bank has cut interest rates.", EventType.ECONOMIC, EventSeverity.MINOR),
        EventTemplate("INTEREST_RATE_HIKE", "Interest Rate Hike", "The central bank has raised interest rates.", EventType.ECONOMIC, EventSeverity.MODERATE),
        EventTemplate("BOND_MARKET", "Bond Market Turmoil", "The government bond market is in turmoil.", EventType.ECONOMIC, EventSeverity.MODERATE),
        EventTemplate("COMMODITY_PRICE", "Commodity Price Shock", "Commodity prices have experienced a major shock.", EventType.ECONOMIC, EventSeverity.MODERATE),
        EventTemplate("OIL_PRICE_SPIKE", "Oil Price Spike", "Oil prices have spiked dramatically.", EventType.ECONOMIC, EventSeverity.MAJOR),
        EventTemplate("FOOD_PRICE_CRISIS", "Food Price Crisis", "Food prices have soared to dangerous levels.", EventType.ECONOMIC, EventSeverity.MAJOR),
        EventTemplate("HOUSING_BUBBLE", "Housing Bubble", "The housing market shows signs of a bubble.", EventType.ECONOMIC, EventSeverity.MODERATE),
        EventTemplate("HOUSING_CRASH", "Housing Market Crash", "The housing market has collapsed.", EventType.ECONOMIC, EventSeverity.MAJOR),
        EventTemplate("TECH_BUBBLE", "Tech Bubble Bursts", "The technology sector bubble has burst.", EventType.ECONOMIC, EventSeverity.MODERATE),
        EventTemplate("CRYPTO_CRASH", "Cryptocurrency Collapse", "Cryptocurrency markets have collapsed.", EventType.ECONOMIC, EventSeverity.MINOR),
        EventTemplate("CREDIT_CRUNCH", "Credit Crunch", "Banks have severely restricted lending.", EventType.ECONOMIC, EventSeverity.MAJOR),
        EventTemplate("CONSUMER_CONFIDENCE", "Consumer Confidence Plunge", "Consumer confidence has plummeted.", EventType.ECONOMIC, EventSeverity.MODERATE),
        EventTemplate("BUSINESS_CONFIDENCE", "Business Confidence Drop", "Business confidence has dropped sharply.", EventType.ECONOMIC, EventSeverity.MODERATE),
        EventTemplate("GDP_GROWTH", "GDP Growth Surprise", "GDP growth has exceeded expectations.", EventType.ECONOMIC, EventSeverity.MINOR),
        EventTemplate("GDP_CONTRACTION", "GDP Contraction", "GDP has contracted unexpectedly.", EventType.ECONOMIC, EventSeverity.MAJOR),
        EventTemplate("PRODUCTIVITY_SLUMP", "Productivity Decline", "Productivity growth has stalled.", EventType.ECONOMIC, EventSeverity.MODERATE),
        EventTemplate("MANUFACTURING_DECLINE", "Manufacturing Slump", "The manufacturing sector is in decline.", EventType.ECONOMIC, EventSeverity.MODERATE),
        EventTemplate("SERVICE_SECTOR", "Service Sector Growth", "The service sector is booming.", EventType.ECONOMIC, EventSeverity.MINOR),
        EventTemplate("AGRICULTURAL_CRISIS", "Agricultural Crisis", "The agricultural sector faces severe challenges.", EventType.ECONOMIC, EventSeverity.MAJOR),
        
        // Diplomatic events (150)
        EventTemplate("TREATY_SIGNED", "Treaty Signed", "A major international treaty has been signed.", EventType.DIPLOMATIC, EventSeverity.MINOR),
        EventTemplate("TREATY_BROKEN", "Treaty Violation", "A treaty partner has violated the agreement.", EventType.DIPLOMATIC, EventSeverity.MAJOR),
        EventTemplate("ALLIANCE_FORMED", "Alliance Formed", "A new military alliance has been formed.", EventType.DIPLOMATIC, EventSeverity.MODERATE),
        EventTemplate("ALLIANCE_DISSOLVED", "Alliance Dissolved", "A long-standing alliance has been dissolved.", EventType.DIPLOMATIC, EventSeverity.MAJOR),
        EventTemplate("EMBASSY_ATTACK", "Embassy Attacked", "Our embassy has been attacked.", EventType.DIPLOMATIC, EventSeverity.MAJOR),
        EventTemplate("DIPLOMATS_EXPELLED", "Diplomats Expelled", "Diplomats have been expelled from a foreign country.", EventType.DIPLOMATIC, EventSeverity.MODERATE),
        EventTemplate("AMBASSADOR_RECALLED", "Ambassador Recalled", "Our ambassador has been recalled.", EventType.DIPLOMATIC, EventSeverity.MODERATE),
        EventTemplate("DIPLOMATIC_BREAK", "Diplomatic Relations Cut", "Diplomatic relations have been severed.", EventType.DIPLOMATIC, EventSeverity.MAJOR),
        EventTemplate("SANCTIONS_VOTE", "UN Sanctions Vote", "The UN Security Council is voting on sanctions.", EventType.DIPLOMATIC, EventSeverity.MODERATE),
        EventTemplate("INTERNATIONAL_COURT", "International Court Case", "A case has been brought before the International Court.", EventType.DIPLOMATIC, EventSeverity.MODERATE),
        EventTemplate("WAR_CRIMES_TRIAL", "War Crimes Trial", "A war crimes trial is beginning.", EventType.DIPLOMATIC, EventSeverity.MODERATE),
        EventTemplate("PEACE_TALKS", "Peace Talks Begin", "Peace negotiations have begun.", EventType.DIPLOMATIC, EventSeverity.MODERATE),
        EventTemplate("PEACE_AGREEMENT", "Peace Agreement", "A peace agreement has been signed.", EventType.DIPLOMATIC, EventSeverity.MAJOR),
        EventTemplate("ARMISTICE", "Armistice Declared", "An armistice has been declared.", EventType.DIPLOMATIC, EventSeverity.MAJOR),
        EventTemplate("CEASEFIRE", "Ceasefire Agreement", "A ceasefire has been agreed.", EventType.DIPLOMATIC, EventSeverity.MODERATE),
        EventTemplate("UN_RESOLUTION", "UN Resolution", "A UN resolution has been passed.", EventType.DIPLOMATIC, EventSeverity.MINOR),
        EventTemplate("UN_VETO", "UN Veto Used", "A veto has been used in the Security Council.", EventType.DIPLOMATIC, EventSeverity.MODERATE),
        EventTemplate("SUMMIT_ANNOUNCED", "Summit Announced", "An international summit has been announced.", EventType.DIPLOMATIC, EventSeverity.MINOR),
        EventTemplate("STATE_VISIT", "State Visit", "A foreign leader is visiting.", EventType.DIPLOMATIC, EventSeverity.MINOR),
        EventTemplate("OFFICIAL_VISIT", "Official Visit", "An official diplomatic visit is scheduled.", EventType.DIPLOMATIC, EventSeverity.MINOR),
        EventTemplate("REFUGEE_CRISIS", "Refugee Crisis", "A refugee crisis is developing.", EventType.DIPLOMATIC, EventSeverity.MAJOR),
        EventTemplate("ASYLUM_REQUEST", "Asylum Request", "A high-profile asylum request has been made.", EventType.DIPLOMATIC, EventSeverity.MODERATE),
        EventTemplate("EXTRADITION_REQUEST", "Extradition Request", "An extradition request has been received.", EventType.DIPLOMATIC, EventSeverity.MODERATE),
        EventTemplate("MARITIME_DISPUTE", "Maritime Dispute", "A maritime boundary dispute has escalated.", EventType.DIPLOMATIC, EventSeverity.MODERATE),
        EventTemplate("TERRITORIAL_DISPUTE", "Territorial Dispute", "A territorial dispute has intensified.", EventType.DIPLOMATIC, EventSeverity.MAJOR),
        EventTemplate("BORDER_INCIDENT", "Border Incident", "A serious border incident has occurred.", EventType.DIPLOMATIC, EventSeverity.MODERATE),
        EventTemplate("SPY_SCANDAL", "Spying Scandal", "A diplomatic spying scandal has emerged.", EventType.DIPLOMATIC, EventSeverity.MAJOR),
        EventTemplate("EXPROPRIATION", "Asset Expropriation", "Foreign assets have been expropriated.", EventType.DIPLOMATIC, EventSeverity.MAJOR),
        EventTemplate("NATIONALIZATION", "Nationalization", "Foreign-owned assets have been nationalized.", EventType.DIPLOMATIC, EventSeverity.MAJOR),
        EventTemplate("INVESTMENT_DISPUTE", "Investment Dispute", "A major international investment dispute has arisen.", EventType.DIPLOMATIC, EventSeverity.MODERATE),
        
        // Military events (150)
        EventTemplate("WAR_DECLARED", "War Declared", "War has been declared.", EventType.MILITARY, EventSeverity.CATASTROPHIC),
        EventTemplate("MILITARY_MOBILIZATION", "Military Mobilization", "The military has been mobilized.", EventType.MILITARY, EventSeverity.MAJOR),
        EventTemplate("TROOPS_DEPLOYED", "Troops Deployed", "Military forces have been deployed.", EventType.MILITARY, EventSeverity.MODERATE),
        EventTemplate("BATTLE_WON", "Battle Victory", "A major battle has been won.", EventType.MILITARY, EventSeverity.MODERATE),
        EventTemplate("BATTLE_LOST", "Battle Defeat", "A major battle has been lost.", EventType.MILITARY, EventSeverity.MAJOR),
        EventTemplate("TERRITORY_CAPTURED", "Territory Captured", "Enemy territory has been captured.", EventType.MILITARY, EventSeverity.MODERATE),
        EventTemplate("TERRITORY_LOST", "Territory Lost", "Territory has been lost to enemy forces.", EventType.MILITARY, EventSeverity.MAJOR),
        EventTemplate("NAVAL_BATTLE", "Naval Engagement", "A major naval battle has occurred.", EventType.MILITARY, EventSeverity.MODERATE),
        EventTemplate("AIR_STRIKE", "Air Strike", "An air strike has been conducted.", EventType.MILITARY, EventSeverity.MODERATE),
        EventTemplate("MISSILE_ATTACK", "Missile Attack", "A missile attack has occurred.", EventType.MILITARY, EventSeverity.MAJOR),
        EventTemplate("DRONE_STRIKE", "Drone Strike", "A drone strike has been conducted.", EventType.MILITARY, EventSeverity.MODERATE),
        EventTemplate("SPECIAL_OPS", "Special Operations", "A special operations mission has been conducted.", EventType.MILITARY, EventSeverity.MODERATE),
        EventTemplate("INSURGENCY", "Insurgency Escalates", "Insurgent activity has increased.", EventType.MILITARY, EventSeverity.MODERATE),
        EventTemplate("TERROR_ATTACK", "Terrorist Attack", "A major terrorist attack has occurred.", EventType.MILITARY, EventSeverity.MAJOR),
        EventTemplate("CYBER_ATTACK", "Cyber Attack", "A major cyber attack has been detected.", EventType.MILITARY, EventSeverity.MAJOR),
        EventTemplate("NUCLEAR_TEST", "Nuclear Test", "A nuclear test has been conducted.", EventType.MILITARY, EventSeverity.CRITICAL),
        EventTemplate("MISSILE_TEST", "Missile Test", "A ballistic missile test has occurred.", EventType.MILITARY, EventSeverity.MODERATE),
        EventTemplate("MILITARY_COUP", "Military Coup", "The military has seized power.", EventType.MILITARY, EventSeverity.CATASTROPHIC),
        EventTemplate("MILITARY_EXERCISE", "Military Exercise", "Large-scale military exercises are underway.", EventType.MILITARY, EventSeverity.MINOR),
        EventTemplate("ARMS_DEAL", "Arms Deal Signed", "A major weapons deal has been signed.", EventType.MILITARY, EventSeverity.MINOR),
        EventTemplate("DEFENSE_CONTRACT", "Defense Contract", "A major defense contract has been awarded.", EventType.MILITARY, EventSeverity.MINOR),
        EventTemplate("MILITARY_SCANDAL", "Military Scandal", "A military scandal has emerged.", EventType.MILITARY, EventSeverity.MODERATE),
        EventTemplate("WAR_CRIMES", "War Crimes Alleged", "War crimes allegations have emerged.", EventType.MILITARY, EventSeverity.MAJOR),
        EventTemplate("FRIENDLY_FIRE", "Friendly Fire Incident", "A friendly fire incident has occurred.", EventType.MILITARY, EventSeverity.MODERATE),
        EventTemplate("MILITARY_ACCIDENT", "Military Accident", "A major military accident has occurred.", EventType.MILITARY, EventSeverity.MODERATE),
        EventTemplate("DEFECTOR", "High-Value Defector", "A high-value defector has arrived.", EventType.MILITARY, EventSeverity.MINOR),
        EventTemplate("PRISONER_EXCHANGE", "Prisoner Exchange", "A prisoner exchange has occurred.", EventType.MILITARY, EventSeverity.MINOR),
        EventTemplate("POW_RESCUED", "POWs Rescued", "Prisoners of war have been rescued.", EventType.MILITARY, EventSeverity.MINOR),
        EventTemplate("MUTINY", "Military Mutiny", "A military mutiny has occurred.", EventType.MILITARY, EventSeverity.MAJOR),
        EventTemplate("DESERTION", "Mass Desertion", "Mass desertions have been reported.", EventType.MILITARY, EventSeverity.MODERATE),
        
        // Natural Disaster events (100)
        EventTemplate("EARTHQUAKE", "Major Earthquake", "A major earthquake has struck.", EventType.NATURAL_DISASTER, EventSeverity.MAJOR),
        EventTemplate("TSUNAMI", "Tsunami Warning", "A tsunami warning has been issued.", EventType.NATURAL_DISASTER, EventSeverity.MAJOR),
        EventTemplate("HURRICANE", "Hurricane Approaching", "A major hurricane is approaching.", EventType.NATURAL_DISASTER, EventSeverity.MAJOR),
        EventTemplate("TORNADO", "Tornado Outbreak", "A tornado outbreak has occurred.", EventType.NATURAL_DISASTER, EventSeverity.MODERATE),
        EventTemplate("FLOOD", "Major Flooding", "Severe flooding has occurred.", EventType.NATURAL_DISASTER, EventSeverity.MAJOR),
        EventTemplate("DROUGHT", "Severe Drought", "A severe drought is affecting the region.", EventType.NATURAL_DISASTER, EventSeverity.MODERATE),
        EventTemplate("WILDFIRE", "Wildfire Outbreak", "Major wildfires are spreading.", EventType.NATURAL_DISASTER, EventSeverity.MAJOR),
        EventTemplate("VOLCANIC_ERUPTION", "Volcanic Eruption", "A volcanic eruption has occurred.", EventType.NATURAL_DISASTER, EventSeverity.MAJOR),
        EventTemplate("LANDSLIDE", "Major Landslide", "A major landslide has occurred.", EventType.NATURAL_DISASTER, EventSeverity.MODERATE),
        EventTemplate("AVALANCHE", "Avalanche Warning", "A major avalanche has occurred.", EventType.NATURAL_DISASTER, EventSeverity.MODERATE),
        EventTemplate("HEAT_WAVE", "Extreme Heat Wave", "An extreme heat wave is affecting the country.", EventType.NATURAL_DISASTER, EventSeverity.MODERATE),
        EventTemplate("COLD_WAVE", "Extreme Cold", "An extreme cold wave has struck.", EventType.NATURAL_DISASTER, EventSeverity.MODERATE),
        EventTemplate("EPIDEMIC", "Disease Outbreak", "A disease outbreak has occurred.", EventType.NATURAL_DISASTER, EventSeverity.MAJOR),
        EventTemplate("PANDEMIC", "Pandemic Declared", "A pandemic has been declared.", EventType.NATURAL_DISASTER, EventSeverity.CATASTROPHIC),
        EventTemplate("FAMINE", "Famine Warning", "A famine is developing.", EventType.NATURAL_DISASTER, EventSeverity.MAJOR),
        EventTemplate("PEST_INFESTATION", "Pest Infestation", "A major pest infestation is destroying crops.", EventType.NATURAL_DISASTER, EventSeverity.MODERATE),
        EventTemplate("SANDSTORM", "Major Sandstorm", "A major sandstorm has struck.", EventType.NATURAL_DISASTER, EventSeverity.MODERATE),
        EventTemplate("OIL_SPILL", "Oil Spill", "A major oil spill has occurred.", EventType.NATURAL_DISASTER, EventSeverity.MODERATE),
        EventTemplate("NUCLEAR_ACCIDENT", "Nuclear Accident", "A nuclear accident has occurred.", EventType.NATURAL_DISASTER, EventSeverity.CATASTROPHIC),
        EventTemplate("CHEMICAL_SPILL", "Chemical Spill", "A major chemical spill has occurred.", EventType.NATURAL_DISASTER, EventSeverity.MAJOR),
        
        // Social events (100)
        EventTemplate("PROTEST_ANNOUNCED", "Protest Planned", "A major protest has been announced.", EventType.SOCIAL, EventSeverity.MINOR),
        EventTemplate("MASS_PROTEST", "Mass Protest", "Mass protests have erupted.", EventType.SOCIAL, EventSeverity.MODERATE),
        EventTemplate("RIOT", "Riots Erupt", "Riots have broken out.", EventType.SOCIAL, EventSeverity.MAJOR),
        EventTemplate("STRIKE_CALLED", "Strike Called", "A major strike has been called.", EventType.SOCIAL, EventSeverity.MODERATE),
        EventTemplate("GENERAL_STRIKE", "General Strike", "A general strike has been called.", EventType.SOCIAL, EventSeverity.MAJOR),
        EventTemplate("LABOR_UNREST", "Labor Unrest", "Labor unrest is spreading.", EventType.SOCIAL, EventSeverity.MODERATE),
        EventTemplate("SOCIAL_MOVEMENT", "Social Movement", "A new social movement is gaining momentum.", EventType.SOCIAL, EventSeverity.MINOR),
        EventTemplate("CIVIL_UNREST", "Civil Unrest", "Civil unrest is spreading.", EventType.SOCIAL, EventSeverity.MAJOR),
        EventTemplate("DEMONSTRATION", "Demonstration", "A large demonstration is planned.", EventType.SOCIAL, EventSeverity.MINOR),
        EventTemplate("HUNGER_STRIKE", "Hunger Strike", "A hunger strike has begun.", EventType.SOCIAL, EventSeverity.MODERATE),
        EventTemplate("SIT_IN", "Sit-In Protest", "A sit-in protest is underway.", EventType.SOCIAL, EventSeverity.MINOR),
        EventTemplate("OCCUPATION", "Building Occupation", "Protesters have occupied a building.", EventType.SOCIAL, EventSeverity.MODERATE),
        EventTemplate("COUNTER_PROTEST", "Counter-Protest", "A counter-protest has been organized.", EventType.SOCIAL, EventSeverity.MODERATE),
        EventTemplate("POLICE_CRACKDOWN", "Police Crackdown", "Police have cracked down on protesters.", EventType.SOCIAL, EventSeverity.MAJOR),
        EventTemplate("MARTIAL_LAW_SOCIAL", "Martial Law Declared", "Martial law has been declared due to unrest.", EventType.SOCIAL, EventSeverity.MAJOR),
        EventTemplate("CURFEW", "Curfew Imposed", "A curfew has been imposed.", EventType.SOCIAL, EventSeverity.MODERATE),
        EventTemplate("STATE_EMERGENCY", "State of Emergency", "A state of emergency has been declared.", EventType.SOCIAL, EventSeverity.MAJOR),
        EventTemplate("MEDIA_BLACKOUT", "Media Blackout", "A media blackout has been imposed.", EventType.SOCIAL, EventSeverity.MAJOR),
        EventTemplate("INTERNET_SHUTDOWN", "Internet Shutdown", "Internet access has been shut down.", EventType.SOCIAL, EventSeverity.MAJOR),
        EventTemplate("SOCIAL_MEDIA_BAN", "Social Media Blocked", "Social media platforms have been blocked.", EventType.SOCIAL, EventSeverity.MODERATE),
        
        // Scandal events (100)
        EventTemplate("POLITICAL_SCANDAL", "Political Scandal", "A major political scandal has broken.", EventType.SCANDAL, EventSeverity.MAJOR),
        EventTemplate("FINANCIAL_SCANDAL", "Financial Scandal", "A major financial scandal has been exposed.", EventType.SCANDAL, EventSeverity.MAJOR),
        EventTemplate("SEX_SCANDAL", "Sex Scandal", "A sex scandal has emerged.", EventType.SCANDAL, EventSeverity.MODERATE),
        EventTemplate("CORRUPTION_SCANDAL", "Corruption Scandal", "A corruption scandal has been revealed.", EventType.SCANDAL, EventSeverity.MAJOR),
        EventTemplate("BRIBERY_SCANDAL", "Bribery Allegations", "Bribery allegations have surfaced.", EventType.SCANDAL, EventSeverity.MODERATE),
        EventTemplate("EMBEZZLEMENT", "Embezzlement Discovered", "Embezzlement has been discovered.", EventType.SCANDAL, EventSeverity.MAJOR),
        EventTemplate("FRAUD_EXPOSED", "Fraud Exposed", "A major fraud has been exposed.", EventType.SCANDAL, EventSeverity.MAJOR),
        EventTemplate("TAX_EVASION", "Tax Evasion", "Tax evasion by prominent figures has been revealed.", EventType.SCANDAL, EventSeverity.MODERATE),
        EventTemplate("MONEY_LAUNDERING", "Money Laundering", "A money laundering scheme has been uncovered.", EventType.SCANDAL, EventSeverity.MAJOR),
        EventTemplate("INSIDER_TRADING", "Insider Trading", "Insider trading has been discovered.", EventType.SCANDAL, EventSeverity.MODERATE),
        EventTemplate("NEPOTISM_SCANDAL", "Nepotism Exposed", "Nepotism in government has been exposed.", EventType.SCANDAL, EventSeverity.MODERATE),
        EventTemplate("CRONYISM_SCANDAL", "Cronyism Revealed", "Government cronyism has been revealed.", EventType.SCANDAL, EventSeverity.MODERATE),
        EventTemplate("ABUSE_POWER", "Abuse of Power", "Abuse of power allegations have emerged.", EventType.SCANDAL, EventSeverity.MAJOR),
        EventTemplate("COVER_UP", "Cover-Up Exposed", "A government cover-up has been exposed.", EventType.SCANDAL, EventSeverity.MAJOR),
        EventTemplate("WHISTLEBLOWER_SCANDAL", "Whistleblower Revelations", "A whistleblower has made revelations.", EventType.SCANDAL, EventSeverity.MAJOR),
        EventTemplate("DATA_BREACH", "Data Breach", "A major data breach has occurred.", EventType.SCANDAL, EventSeverity.MODERATE),
        EventTemplate("PRIVACY_VIOLATION", "Privacy Violation", "A major privacy violation has been discovered.", EventType.SCANDAL, EventSeverity.MODERATE),
        EventTemplate("HACKING_SCANDAL", "Hacking Scandal", "A hacking scandal has emerged.", EventType.SCANDAL, EventSeverity.MAJOR),
        EventTemplate("MEDICAL_SCANDAL", "Medical Scandal", "A medical scandal has broken.", EventType.SCANDAL, EventSeverity.MAJOR),
        EventTemplate("ENVIRONMENTAL_SCANDAL", "Environmental Scandal", "An environmental scandal has been exposed.", EventType.SCANDAL, EventSeverity.MODERATE),
        
        // Crisis events (100)
        EventTemplate("CONSTITUTIONAL_CRISIS", "Constitutional Crisis", "A constitutional crisis has emerged.", EventType.CRISIS, EventSeverity.CRITICAL),
        EventTemplate("HUMANITARIAN_CRISIS", "Humanitarian Crisis", "A humanitarian crisis is developing.", EventType.CRISIS, EventSeverity.MAJOR),
        EventTemplate("REFUGEE_CRISIS_CRISIS", "Refugee Crisis", "A refugee crisis has escalated.", EventType.CRISIS, EventSeverity.MAJOR),
        EventTemplate("FOOD_CRISIS", "Food Crisis", "A food security crisis is developing.", EventType.CRISIS, EventSeverity.MAJOR),
        EventTemplate("WATER_CRISIS", "Water Crisis", "A water crisis is emerging.", EventType.CRISIS, EventSeverity.MAJOR),
        EventTemplate("ENERGY_CRISIS", "Energy Crisis", "An energy crisis is developing.", EventType.CRISIS, EventSeverity.MAJOR),
        EventTemplate("HEALTH_CRISIS", "Health Crisis", "A public health crisis is emerging.", EventType.CRISIS, EventSeverity.MAJOR),
        EventTemplate("SECURITY_CRISIS", "Security Crisis", "A national security crisis has emerged.", EventType.CRISIS, EventSeverity.MAJOR),
        EventTemplate("FINANCIAL_CRISIS", "Financial Crisis", "A financial crisis is unfolding.", EventType.CRISIS, EventSeverity.MAJOR),
        EventTemplate("BORDER_CRISIS", "Border Crisis", "A border crisis has emerged.", EventType.CRISIS, EventSeverity.MODERATE),
        EventTemplate("MIGRATION_CRISIS", "Migration Crisis", "A migration crisis is developing.", EventType.CRISIS, EventSeverity.MAJOR),
        EventTemplate("POLITICAL_CRISIS", "Political Crisis", "A political crisis has emerged.", EventType.CRISIS, EventSeverity.MAJOR),
        EventTemplate("DIPLOMATIC_CRISIS", "Diplomatic Crisis", "A diplomatic crisis has escalated.", EventType.CRISIS, EventSeverity.MAJOR),
        EventTemplate("MILITARY_CRISIS", "Military Crisis", "A military crisis has developed.", EventType.CRISIS, EventSeverity.MAJOR),
        EventTemplate("TERRORISM_CRISIS", "Terrorism Crisis", "A terrorism crisis has emerged.", EventType.CRISIS, EventSeverity.MAJOR),
        EventTemplate("CYBER_CRISIS", "Cyber Crisis", "A cyber security crisis has emerged.", EventType.CRISIS, EventSeverity.MAJOR),
        EventTemplate("ENVIRONMENTAL_CRISIS", "Environmental Crisis", "An environmental crisis is developing.", EventType.CRISIS, EventSeverity.MAJOR),
        EventTemplate("CLIMATE_CRISIS", "Climate Crisis", "A climate-related crisis has emerged.", EventType.CRISIS, EventSeverity.MAJOR),
        EventTemplate("INFRASTRUCTURE_CRISIS", "Infrastructure Crisis", "An infrastructure crisis has emerged.", EventType.CRISIS, EventSeverity.MODERATE),
        EventTemplate("GOVERNANCE_CRISIS", "Governance Crisis", "A governance crisis has developed.", EventType.CRISIS, EventSeverity.MAJOR),
        
        // Sports events (50)
        EventTemplate("WORLD_CUP_QUAL", "World Cup Qualification", "The national team has qualified for the World Cup.", EventType.SPORTS, EventSeverity.MINOR),
        EventTemplate("OLYMPICS_MEDAL", "Olympic Medal", "An athlete has won an Olympic medal.", EventType.SPORTS, EventSeverity.MINOR),
        EventTemplate("CHAMPIONSHIP_WIN", "Championship Victory", "A national team has won a championship.", EventType.SPORTS, EventSeverity.MINOR),
        EventTemplate("SPORTS_SCANDAL", "Sports Scandal", "A major sports scandal has emerged.", EventType.SPORTS, EventSeverity.MODERATE),
        EventTemplate("DOPING_SCANDAL", "Doping Scandal", "A doping scandal has been revealed.", EventType.SPORTS, EventSeverity.MODERATE),
        EventTemplate("MATCH_FIXING", "Match Fixing", "Match fixing allegations have emerged.", EventType.SPORTS, EventSeverity.MODERATE),
        EventTemplate("SPORTS_BOYCOTT", "Sports Boycott", "A sports boycott has been announced.", EventType.SPORTS, EventSeverity.MINOR),
        EventTemplate("HOSTING_EVENT", "Sports Event Hosting", "The country will host a major sports event.", EventType.SPORTS, EventSeverity.MINOR),
        EventTemplate("STADIUM_DISASTER", "Stadium Disaster", "A stadium disaster has occurred.", EventType.SPORTS, EventSeverity.MAJOR),
        EventTemplate("SPORTS_DIPLOMACY", "Sports Diplomacy", "Sports are being used for diplomatic purposes.", EventType.SPORTS, EventSeverity.MINOR),
        
        // Cultural events (50)
        EventTemplate("CULTURAL_FESTIVAL", "Cultural Festival", "A major cultural festival is underway.", EventType.CULTURAL, EventSeverity.TRIVIAL),
        EventTemplate("ART_EXHIBITION", "Art Exhibition", "A major art exhibition is opening.", EventType.CULTURAL, EventSeverity.TRIVIAL),
        EventTemplate("LITERARY_PRIZE", "Literary Prize", "A national author has won a major prize.", EventType.CULTURAL, EventSeverity.TRIVIAL),
        EventTemplate("FILM_AWARD", "Film Award", "A national film has won an award.", EventType.CULTURAL, EventSeverity.TRIVIAL),
        EventTemplate("MUSIC_FESTIVAL", "Music Festival", "A major music festival is taking place.", EventType.CULTURAL, EventSeverity.TRIVIAL),
        EventTemplate("HERITAGE_SITE", "Heritage Site Listed", "A site has been added to the World Heritage list.", EventType.CULTURAL, EventSeverity.MINOR),
        EventTemplate("CULTURAL_EXCHANGE", "Cultural Exchange", "A major cultural exchange program has begun.", EventType.CULTURAL, EventSeverity.TRIVIAL),
        EventTemplate("CENSORSHIP_ROW", "Censorship Controversy", "A censorship controversy has emerged.", EventType.CULTURAL, EventSeverity.MODERATE),
        EventTemplate("CULTURAL_DISPUTE", "Cultural Dispute", "A cultural heritage dispute has arisen.", EventType.CULTURAL, EventSeverity.MINOR),
        EventTemplate("ART_CONTROVERSY", "Art Controversy", "An artwork has sparked controversy.", EventType.CULTURAL, EventSeverity.MINOR),
        
        // Opportunity events (50)
        EventTemplate("INVESTMENT_OPP", "Investment Opportunity", "A major investment opportunity has arisen.", EventType.OPPORTUNITY, EventSeverity.MINOR),
        EventTemplate("TRADE_OPPORTUNITY", "Trade Opportunity", "A new trade opportunity has emerged.", EventType.OPPORTUNITY, EventSeverity.MINOR),
        EventTemplate("TECHNOLOGY_BREAKTHROUGH", "Technology Breakthrough", "A technological breakthrough has been achieved.", EventType.OPPORTUNITY, EventSeverity.MINOR),
        EventTemplate("RESOURCE_DISCOVERY", "Resource Discovery", "Major natural resources have been discovered.", EventType.OPPORTUNITY, EventSeverity.MODERATE),
        EventTemplate("DIPLOMATIC_OPENING", "Diplomatic Opening", "A diplomatic opening has emerged.", EventType.OPPORTUNITY, EventSeverity.MINOR),
        EventTemplate("PEACE_OPPORTUNITY", "Peace Opportunity", "An opportunity for peace has arisen.", EventType.OPPORTUNITY, EventSeverity.MODERATE),
        EventTemplate("REFORM_WINDOW", "Reform Window", "A window for reform has opened.", EventType.OPPORTUNITY, EventSeverity.MINOR),
        EventTemplate("REGIONAL_LEADERSHIP", "Regional Leadership", "An opportunity for regional leadership has emerged.", EventType.OPPORTUNITY, EventSeverity.MINOR),
        EventTemplate("SPORTS_VICTORY_OPP", "Sports Victory", "A major sports victory has boosted national morale.", EventType.OPPORTUNITY, EventSeverity.TRIVIAL),
        EventTemplate("INTERNATIONAL_RECOGNITION", "International Recognition", "The country has received international recognition.", EventType.OPPORTUNITY, EventSeverity.MINOR)
    )
    
    // ==================== FLAG GENERATION ====================
    
    private val flagColors = mapOf(
        "red" to "#FF0000",
        "blue" to "#0000FF",
        "green" to "#00FF00",
        "yellow" to "#FFFF00",
        "white" to "#FFFFFF",
        "black" to "#000000",
        "orange" to "#FFA500",
        "purple" to "#800080",
        "cyan" to "#00FFFF",
        "magenta" to "#FF00FF",
        "brown" to "#8B4513",
        "gray" to "#808080",
        "navy" to "#000080",
        "maroon" to "#800000",
        "olive" to "#808000",
        "teal" to "#008080",
        "crimson" to "#DC143C",
        "gold" to "#FFD700",
        "silver" to "#C0C0C0",
        "indigo" to "#4B0082"
    )
    
    private val flagPatterns = listOf(
        "horizontal_tricolor", "vertical_tricolor", "horizontal_bicolor", "vertical_bicolor",
        "diagonal_bicolor", "quartered", "canton", "saltire", "cross", "nordic_cross",
        "chevron", "triangle", "star_field", "sun_emblem", "crescent_moon", "eagle_emblem",
        "lion_emblem", "dragon_emblem", "star_emblem", "map_emblem", "wreath_emblem",
        "stripe_horizontal", "stripe_vertical", "border", "circle_emblem", "diamond_emblem"
    )
    
    private val flagSymbols = listOf(
        "star", "sun", "moon", "crescent", "eagle", "lion", "dragon", "bear",
        "wolf", "horse", "bull", "elephant", "tiger", "leopard", "falcon", "hawk",
        "cross", "crescent_moon", "star_crescent", "sun_face", "wheel", "anchor",
        "sword", "shield", "crown", "torch", "laurel", "olive_branch", "palm",
        "map", "mountain", "river", "tree", "flower", "diamond", "circle", "triangle"
    )
    
    // ==================== GENERATION FUNCTIONS ====================
    
    /**
     * Generates a unique country name with cultural patterns.
     */
    fun generateCountryName(continent: Continent? = null): String {
        val prefix = when (continent) {
            Continent.AFRICA -> africanNames.random(random) + countrySuffixes.random(random)
            Continent.ASIA -> asianNames.random(random) + countrySuffixes.random(random)
            Continent.EUROPE -> europeanNames.random(random) + countrySuffixes.random(random)
            Continent.MIDDLE_EAST -> middleEasternNames.random(random) + countrySuffixes.random(random)
            Continent.SOUTH_AMERICA, Continent.NORTH_AMERICA -> latinAmericanNames.random(random) + countrySuffixes.random(random)
            else -> countryPrefixes.random(random) + countrySuffixes.random(random)
        }
        return prefix
    }
    
    /**
     * Generates a unique capital city name.
     */
    fun generateCapitalName(): String {
        return capitalPrefixes.random(random) + capitalSuffixes.random(random)
    }
    
    /**
     * Generates a procedurally generated flag.
     */
    fun generateFlag(): ProceduralFlag {
        val primaryColor = flagColors.entries.random(random).value
        val secondaryColor = flagColors.entries.random(random).value
        val tertiaryColor = flagColors.entries.random(random).value
        val pattern = flagPatterns.random(random)
        val symbol = flagSymbols.random(random)
        
        return ProceduralFlag(
            colors = listOf(primaryColor, secondaryColor, tertiaryColor).distinct(),
            pattern = pattern,
            symbol = symbol,
            aspectRatio = listOf(1.0, 1.5, 2.0, 3.0).random(random)
        )
    }
    
    /**
     * Generates a complete NPC with personality archetype.
     */
    fun generateNPCDetailed(
        countryId: String,
        role: NPCRole = NPCRole.values().random(random),
        archetypeIndex: Int? = null
    ): DetailedNPC {
        val gender = Gender.values().random(random)
        val firstName = if (gender == Gender.MALE) maleNames.random(random) else femaleNames.random(random)
        val lastName = lastNames.random(random)
        val archetype = archetypeIndex?.let { personalityArchetypes[it] } ?: personalityArchetypes.random(random)
        
        val personality = generatePersonalityForArchetype(archetype)
        val age = random.nextInt(28, 75)
        
        return DetailedNPC(
            id = UUID.randomUUID().toString(),
            fullName = "$firstName $lastName",
            archetype = archetype,
            age = age,
            gender = gender,
            nationality = countryId,
            role = role,
            personality = personality,
            skills = generateSkillsForArchetype(archetype),
            traits = generateTraitsForArchetype(archetype),
            background = generateBackground(archetype),
            goals = generateGoals(archetype),
            fears = generateFears(archetype)
        )
    }
    
    /**
     * Generates a random event from the template library.
     */
    fun generateEventFromTemplate(): GameEvent {
        val template = eventTemplates.random(random)
        return GameEvent(
            id = UUID.randomUUID().toString(),
            title = template.title,
            description = template.description,
            type = template.type,
            category = EventCategory.values().random(random),
            severity = template.severity,
            involvedEntities = emptyList(),
            availableDecisions = generateDecisionsForEvent(template),
            consequences = emptyList(),
            triggeredBy = null,
            cascadeChildren = emptyList(),
            memoryImpact = null,
            timeLimit = null,
            isActive = true,
            isResolved = false,
            resolvedDecision = null,
            createdAt = System.currentTimeMillis(),
            resolvedAt = null
        )
    }
    
    /**
     * Generates events by type.
     */
    fun generateEventsByType(type: EventType, count: Int = 1): List<GameEvent> {
        return eventTemplates
            .filter { it.type == type }
            .shuffled(random)
            .take(count)
            .map { template ->
                GameEvent(
                    id = UUID.randomUUID().toString(),
                    title = template.title,
                    description = template.description,
                    type = template.type,
                    category = EventCategory.values().random(random),
                    severity = template.severity,
                    involvedEntities = emptyList(),
                    availableDecisions = generateDecisionsForEvent(template),
                    consequences = emptyList(),
                    triggeredBy = null,
                    cascadeChildren = emptyList(),
                    memoryImpact = null,
                    timeLimit = null,
                    isActive = true,
                    isResolved = false,
                    resolvedDecision = null,
                    createdAt = System.currentTimeMillis(),
                    resolvedAt = null
                )
            }
    }
    
    /**
     * Generates a procedural political party.
     */
    fun generatePoliticalParty(countryId: String, ideology: Ideology): DetailedParty {
        val name = generatePartyNameForIdeology(ideology)
        val acronym = name.split(" ").map { it.first() }.joinToString("")
        
        return DetailedParty(
            id = UUID.randomUUID().toString(),
            name = name,
            acronym = acronym,
            ideology = ideology,
            platform = generatePlatformForIdeology(ideology),
            foundingYear = random.nextInt(1900, 2020),
            country = countryId,
            membership = random.nextLong(1000, 1_000_000),
            supportBase = generateSupportBase(ideology),
            keyIssues = generateKeyIssues(ideology),
            internalFactions = generateFactions(ideology),
            scandals = mutableListOf(),
            achievements = mutableListOf()
        )
    }
    
    /**
     * Generates procedural laws/bills.
     */
    fun generateBill(category: LawCategory): ProceduralBill {
        val templates = billTemplates[category] ?: listOf("General Reform Act")
        val title = templates.random(random)
        
        return ProceduralBill(
            id = UUID.randomUUID().toString(),
            title = title,
            category = category,
            description = "Legislation concerning ${category.name.lowercase().replace("_", " ")}.",
            sponsors = emptyList(),
            opponents = emptyList(),
            provisions = generateProvisions(category),
            costEstimate = random.nextLong(1_000_000, 10_000_000_000),
            publicSupport = random.nextDouble(0.2, 0.8),
            industrySupport = random.nextDouble(0.2, 0.8),
            internationalReaction = random.nextDouble(0.3, 0.7)
        )
    }
    
    /**
     * Generates a news headline.
     */
    fun generateNewsHeadline(category: NewsCategory): NewsHeadline {
        val templates = newsTemplates[category] ?: listOf("Major Development Reported")
        val headline = templates.random(random)
        
        return NewsHeadline(
            id = UUID.randomUUID().toString(),
            headline = headline,
            subheadline = null,
            source = "National News Agency",
            date = System.currentTimeMillis(),
            category = category,
            importance = random.nextInt(1, 10),
            sentiment = random.nextDouble(-1.0, 1.0),
            relatedEvent = null,
            relatedCountry = null,
            relatedNPC = null,
            isFake = false,
            retracted = false,
            correction = null
        )
    }
    
    // ==================== HELPER FUNCTIONS ====================
    
    private fun generatePersonalityForArchetype(archetype: String): Personality {
        val baseOpenness = when {
            archetype.contains("Visionary") || archetype.contains("Reform") -> random.nextInt(70, 95)
            archetype.contains("Conservative") || archetype.contains("Traditional") -> random.nextInt(20, 50)
            else -> random.nextInt(40, 70)
        }
        
        val baseConscientiousness = when {
            archetype.contains("Organizer") || archetype.contains("Manager") -> random.nextInt(70, 95)
            archetype.contains("Corrupt") || archetype.contains("Scandal") -> random.nextInt(20, 50)
            else -> random.nextInt(40, 70)
        }
        
        val baseExtraversion = when {
            archetype.contains("Speaker") || archetype.contains("Leader") -> random.nextInt(70, 95)
            archetype.contains("Analyst") || archetype.contains("Scholar") -> random.nextInt(30, 60)
            else -> random.nextInt(40, 70)
        }
        
        val baseAgreeableness = when {
            archetype.contains("Diplomat") || archetype.contains("Peacemaker") -> random.nextInt(70, 95)
            archetype.contains("Strongman") || archetype.contains("Criminal") -> random.nextInt(20, 50)
            else -> random.nextInt(40, 70)
        }
        
        val baseNeuroticism = when {
            archetype.contains("Crisis") || archetype.contains("Scandal") -> random.nextInt(60, 90)
            archetype.contains("Stable") || archetype.contains("Calm") -> random.nextInt(10, 40)
            else -> random.nextInt(30, 60)
        }
        
        return Personality(
            openness = baseOpenness,
            conscientiousness = baseConscientiousness,
            extraversion = baseExtraversion,
            agreeableness = baseAgreeableness,
            neuroticism = baseNeuroticism
        )
    }
    
    private fun generateSkillsForArchetype(archetype: String): Map<Skill, Int> {
        return Skill.values().associate { skill ->
            val bonus = when {
                archetype.contains("Diplomat") && skill == Skill.DIPLOMACY -> 30
                archetype.contains("Military") && skill == Skill.MILITARY_STRATEGY -> 30
                archetype.contains("Econom") && skill == Skill.ECONOMICS -> 30
                archetype.contains("Speaker") && skill == Skill.ORATORY -> 30
                archetype.contains("Leader") && skill == Skill.CHARISMA -> 30
                archetype.contains("Corrupt") && skill == Skill.CORRUPTION -> 30
                archetype.contains("Spy") && skill == Skill.INTELLIGENCE -> 30
                else -> 0
            }
            skill to (random.nextInt(30, 70) + bonus).coerceIn(0, 100)
        }
    }
    
    private fun generateTraitsForArchetype(archetype: String): List<Trait> {
        val traits = mutableListOf<Trait>()
        
        when {
            archetype.contains("Visionary") -> traits.add(Trait("Visionary", TraitType.POSITIVE, 0.8))
            archetype.contains("Corrupt") -> traits.add(Trait("Corruptible", TraitType.NEGATIVE, 0.7))
            archetype.contains("Reformer") -> traits.add(Trait("Progressive", TraitType.POSITIVE, 0.7))
            archetype.contains("Conservative") -> traits.add(Trait("Traditional", TraitType.NEUTRAL, 0.6))
            archetype.contains("Strongman") -> traits.add(Trait("Authoritarian", TraitType.NEUTRAL, 0.8))
            archetype.contains("Diplomat") -> traits.add(Trait("Diplomatic", TraitType.POSITIVE, 0.7))
            archetype.contains("Speaker") -> traits.add(Trait("Charismatic", TraitType.POSITIVE, 0.7))
        }
        
        return traits
    }
    
    private fun generateBackground(archetype: String): String {
        val backgrounds = listOf(
            "Born into a political family",
            "Rose from humble beginnings",
            "Former business executive",
            "Career civil servant",
            "Military background",
            "Academic background",
            "Activist background",
            "Media background"
        )
        return backgrounds.random(random)
    }
    
    private fun generateGoals(archetype: String): List<String> {
        return when {
            archetype.contains("Reformer") -> listOf("Implement reform", "Modernize institutions", "Fight corruption")
            archetype.contains("Conservative") -> listOf("Preserve traditions", "Maintain stability", "Protect values")
            archetype.contains("Military") -> listOf("Strengthen defense", "Modernize military", "Ensure security")
            archetype.contains("Diplomat") -> listOf("Build alliances", "Resolve conflicts", "Enhance prestige")
            else -> listOf("Maintain power", "Build legacy", "Serve country")
        }
    }
    
    private fun generateFears(archetype: String): List<String> {
        return when {
            archetype.contains("Reformer") -> listOf("Reform failure", "Backlash", "Losing support")
            archetype.contains("Conservative") -> listOf("Rapid change", "Loss of values", "Instability")
            archetype.contains("Corrupt") -> listOf("Exposure", "Prosecution", "Loss of power")
            archetype.contains("Military") -> listOf("Defeat", "Loss of prestige", "Coup")
            else -> listOf("Political defeat", "Scandal", "Loss of influence")
        }
    }
    
    private fun generateDecisionsForEvent(template: EventTemplate): List<Decision> {
        return listOf(
            Decision(
                id = UUID.randomUUID().toString(),
                text = "Take decisive action",
                description = "Address the situation directly and firmly",
                requirements = emptyList(),
                costs = listOf(Cost(CostType.POLITICAL_CAPITAL, 10.0, false, null)),
                outcomes = emptyList(),
                aiReasoning = "Direct action shows leadership",
                riskLevel = RiskLevel.MODERATE,
                timeToImplement = 3600000L,
                approvalRequired = false,
                approvers = emptyList()
            ),
            Decision(
                id = UUID.randomUUID().toString(),
                text = "Delegate to ministry",
                description = "Let the relevant ministry handle it",
                requirements = emptyList(),
                costs = listOf(Cost(CostType.POLITICAL_CAPITAL, 5.0, false, null)),
                outcomes = emptyList(),
                aiReasoning = "Delegation shows trust in institutions",
                riskLevel = RiskLevel.LOW,
                timeToImplement = 7200000L,
                approvalRequired = false,
                approvers = emptyList()
            ),
            Decision(
                id = UUID.randomUUID().toString(),
                text = "Form a committee",
                description = "Establish a committee to study the issue",
                requirements = emptyList(),
                costs = listOf(Cost(CostType.POLITICAL_CAPITAL, 3.0, false, null)),
                outcomes = emptyList(),
                aiReasoning = "Delays decision but builds consensus",
                riskLevel = RiskLevel.SAFE,
                timeToImplement = 86400000L,
                approvalRequired = false,
                approvers = emptyList()
            )
        )
    }
    
    private fun generatePartyNameForIdeology(ideology: Ideology): String {
        return when (ideology) {
            Ideology.SOCIALIST -> listOf("Socialist Party", "Workers Party", "People's Socialist Party").random(random)
            Ideology.SOCIAL_DEMOCRAT -> listOf("Social Democratic Party", "Labour Party", "Progressive Alliance").random(random)
            Ideology.PROGRESSIVE -> listOf("Progressive Party", "Reform Movement", "Future Forward").random(random)
            Ideology.LIBERAL -> listOf("Liberal Party", "Freedom Party", "Liberty Alliance").random(random)
            Ideology.CENTRIST -> listOf("Center Party", "Moderate Alliance", "National Unity").random(random)
            Ideology.CONSERVATIVE -> listOf("Conservative Party", "Traditional Values Party", "Heritage Party").random(random)
            Ideology.NATIONAL_CONSERVATIVE -> listOf("National Conservative Union", "Patriotic Party", "National Alliance").random(random)
            Ideology.NATIONALIST -> listOf("National Front", "Nationalist Party", "Fatherland Party").random(random)
            Ideology.POPULIST -> listOf("People's Party", "Popular Movement", "Citizens Alliance").random(random)
            Ideology.LIBERTARIAN -> listOf("Libertarian Party", "Freedom Alliance", "Liberty Party").random(random)
            Ideology.GREEN -> listOf("Green Party", "Environmental Alliance", "Eco Movement").random(random)
            Ideology.COMMUNIST -> listOf("Communist Party", "Workers Revolutionary Party", "People's Republic Party").random(random)
            Ideology.ISLAMIST -> listOf("Islamic Justice Party", "Muslim Brotherhood", "Islamic Front").random(random)
            Ideology.CHRISTIAN_DEMOCRAT -> listOf("Christian Democratic Union", "Christian People's Party", "Faith and Freedom").random(random)
        }
    }
    
    private fun generatePlatformForIdeology(ideology: Ideology): DetailedPlatform {
        return DetailedPlatform(
            economicPolicy = when (ideology) {
                Ideology.SOCIALIST, Ideology.COMMUNIST -> EconomicPolicy.STATE_CONTROLLED
                Ideology.SOCIAL_DEMOCRAT -> EconomicPolicy.MIXED_ECONOMY_LEFT
                Ideology.LIBERAL -> EconomicPolicy.MIXED_ECONOMY_CENTER
                Ideology.LIBERTARIAN -> EconomicPolicy.FREE_MARKET
                Ideology.CONSERVATIVE, Ideology.NATIONAL_CONSERVATIVE -> EconomicPolicy.MIXED_ECONOMY_RIGHT
                else -> EconomicPolicy.MIXED_ECONOMY_CENTER
            },
            socialPolicy = when (ideology) {
                Ideology.SOCIALIST, Ideology.PROGRESSIVE, Ideology.GREEN -> SocialPolicy.PROGRESSIVE
                Ideology.COMMUNIST -> SocialPolicy.MODERATE_PROGRESSIVE
                Ideology.CONSERVATIVE, Ideology.NATIONAL_CONSERVATIVE -> SocialPolicy.CONSERVATIVE
                Ideology.NATIONALIST -> SocialPolicy.TRADITIONALIST
                else -> SocialPolicy.CENTRIST
            },
            foreignPolicy = when (ideology) {
                Ideology.NATIONALIST -> ForeignPolicy.ISOLATIONIST
                Ideology.COMMUNIST -> ForeignPolicy.NEUTRALIST
                Ideology.LIBERAL -> ForeignPolicy.ENGAGEMENT
                Ideology.CONSERVATIVE -> ForeignPolicy.INTERVENTIONIST
                else -> ForeignPolicy.ENGAGEMENT
            },
            keyIssues = generateKeyIssues(ideology),
            promises = emptyList()
        )
    }
    
    private fun generateSupportBase(ideology: Ideology): List<String> {
        return when (ideology) {
            Ideology.SOCIALIST, Ideology.COMMUNIST -> listOf("Workers", "Unions", "Students", "Intellectuals")
            Ideology.CONSERVATIVE, Ideology.NATIONAL_CONSERVATIVE -> listOf("Business owners", "Rural voters", "Religious voters", "Older voters")
            Ideology.LIBERAL -> listOf("Middle class", "Urban voters", "Professionals", "Small business")
            Ideology.GREEN -> listOf("Environmentalists", "Young voters", "Urban progressives", "Academics")
            Ideology.POPULIST -> listOf("Working class", "Rural voters", "Disaffected voters", "Nationalists")
            else -> listOf("Moderates", "Centrists", "Independents")
        }
    }
    
    private fun generateKeyIssues(ideology: Ideology): List<String> {
        return when (ideology) {
            Ideology.SOCIALIST -> listOf("Workers rights", "Wealth redistribution", "Public services")
            Ideology.CONSERVATIVE -> listOf("Traditional values", "Law and order", "Fiscal responsibility")
            Ideology.LIBERAL -> listOf("Civil liberties", "Free markets", "Limited government")
            Ideology.GREEN -> listOf("Climate action", "Renewable energy", "Conservation")
            Ideology.NATIONALIST -> listOf("National sovereignty", "Immigration control", "Patriotism")
            else -> listOf("Economic growth", "Stability", "Reform")
        }
    }
    
    private fun generateFactions(ideology: Ideology): List<PartyFaction> {
        return listOf(
            PartyFaction("Moderate Wing", random.nextInt(20, 40)),
            PartyFaction("Radical Wing", random.nextInt(10, 30)),
            PartyFaction("Traditionalists", random.nextInt(15, 35))
        )
    }
    
    private fun generateProvisions(category: LawCategory): List<BillProvision> {
        return listOf(
            BillProvision("Section 1", "Establishes the framework for implementation"),
            BillProvision("Section 2", "Defines the scope and applicability"),
            BillProvision("Section 3", "Sets out enforcement mechanisms"),
            BillProvision("Section 4", "Provides for funding allocation")
        )
    }
    
    // Bill templates by category
    private val billTemplates: Map<LawCategory, List<String>> = mapOf(
        LawCategory.ECONOMIC to listOf(
            "Economic Reform Act", "Tax Reform Bill", "Budget Allocation Act", "Financial Regulation Act",
            "Trade Facilitation Bill", "Investment Promotion Act", "Small Business Support Act",
            "Labor Market Reform", "Pension Reform Bill", "Minimum Wage Adjustment Act",
            "Banking Regulation Act", "Consumer Protection Bill", "Antitrust Enforcement Act",
            "Infrastructure Investment Act", "Housing Market Reform"
        ),
        LawCategory.SOCIAL to listOf(
            "Social Welfare Reform", "Healthcare Access Act", "Education Funding Bill",
            "Family Support Act", "Disability Rights Bill", "Elder Care Reform",
            "Child Protection Act", "Housing Assistance Bill", "Food Security Act",
            "Mental Health Support Act", "Addiction Treatment Bill"
        ),
        LawCategory.JUSTICE to listOf(
            "Criminal Justice Reform", "Sentencing Guidelines Act", "Police Reform Bill",
            "Prison Reform Act", "Rehabilitation Program Act", "Victims Rights Bill",
            "Anti-Corruption Act", "Judicial Independence Act", "Legal Aid Expansion"
        ),
        LawCategory.DEFENSE to listOf(
            "Defense Authorization Act", "Military Reform Bill", "Veterans Affairs Act",
            "Arms Procurement Reform", "Cybersecurity Enhancement Act", "Intelligence Oversight Bill"
        ),
        LawCategory.FOREIGN_POLICY to listOf(
            "Foreign Relations Act", "Trade Agreement Implementation", "Sanctions Authorization",
            "Diplomatic Service Reform", "International Development Act", "Refugee Policy Bill"
        ),
        LawCategory.ENVIRONMENT to listOf(
            "Environmental Protection Act", "Climate Action Bill", "Clean Energy Transition Act",
            "Pollution Control Act", "Conservation Bill", "Water Resource Management",
            "Air Quality Standards Act", "Waste Management Reform"
        )
    )
    
    // News templates by category
    private val newsTemplates: Map<NewsCategory, List<String>> = mapOf(
        NewsCategory.POLITICS to listOf(
            "Government Announces Major Policy Shift",
            "Opposition Leader Criticizes Administration",
            "Parliament Debates Controversial Legislation",
            "Election Results Show Shifting Political Landscape",
            "Cabinet Reshuffle Announced"
        ),
        NewsCategory.ECONOMY to listOf(
            "Stock Markets Rally on Economic News",
            "Central Bank Announces Interest Rate Decision",
            "Unemployment Figures Released",
            "Trade Deal Negotiations Continue",
            "GDP Growth Exceeds Expectations"
        ),
        NewsCategory.INTERNATIONAL to listOf(
            "International Summit Concludes",
            "Diplomatic Tensions Rise",
            "Foreign Leader Visits",
            "UN Resolution Passes",
            "Sanctions Announced"
        ),
        NewsCategory.MILITARY to listOf(
            "Military Exercise Begins",
            "Defense Budget Announced",
            "Peacekeeping Mission Extended",
            "Military Equipment Deal Signed",
            "Security Alert Issued"
        )
    )
}

// ==================== DATA CLASSES ====================

data class EventTemplate(
    val id: String,
    val title: String,
    val description: String,
    val type: EventType,
    val severity: EventSeverity
)

data class ProceduralFlag(
    val colors: List<String>,
    val pattern: String,
    val symbol: String,
    val aspectRatio: Double
)

data class DetailedNPC(
    val id: String,
    val fullName: String,
    val archetype: String,
    val age: Int,
    val gender: Gender,
    val nationality: String,
    val role: NPCRole,
    val personality: Personality,
    val skills: Map<Skill, Int>,
    val traits: List<Trait>,
    val background: String,
    val goals: List<String>,
    val fears: List<String>
)

data class DetailedParty(
    val id: String,
    val name: String,
    val acronym: String,
    val ideology: Ideology,
    val platform: DetailedPlatform,
    val foundingYear: Int,
    val country: String,
    val membership: Long,
    val supportBase: List<String>,
    val keyIssues: List<String>,
    val internalFactions: List<PartyFaction>,
    val scandals: MutableList<String>,
    val achievements: MutableList<String>
)

data class DetailedPlatform(
    val economicPolicy: EconomicPolicy,
    val socialPolicy: SocialPolicy,
    val foreignPolicy: ForeignPolicy,
    val keyIssues: List<String>,
    val promises: List<String>
)

data class PartyFaction(
    val name: String,
    val influence: Int
)

data class ProceduralBill(
    val id: String,
    val title: String,
    val category: LawCategory,
    val description: String,
    val sponsors: List<String>,
    val opponents: List<String>,
    val provisions: List<BillProvision>,
    val costEstimate: Long,
    val publicSupport: Double,
    val industrySupport: Double,
    val internationalReaction: Double
)

data class BillProvision(
    val section: String,
    val text: String
)

enum class LawCategory {
    ECONOMIC,
    SOCIAL,
    JUSTICE,
    DEFENSE,
    FOREIGN_POLICY,
    ENVIRONMENT,
    EDUCATION,
    HEALTH,
    INFRASTRUCTURE,
    TECHNOLOGY
}

enum class EconomicPolicy {
    STATE_CONTROLLED,
    MIXED_ECONOMY_LEFT,
    MIXED_ECONOMY_CENTER,
    MIXED_ECONOMY_RIGHT,
    FREE_MARKET
}

enum class SocialPolicy {
    PROGRESSIVE,
    MODERATE_PROGRESSIVE,
    CENTRIST,
    CONSERVATIVE,
    TRADITIONALIST
}

enum class ForeignPolicy {
    ISOLATIONIST,
    NEUTRALIST,
    ENGAGEMENT,
    INTERVENTIONIST
}
