package osu.cs362.URLValidator;

import java.util.*;

public class UrlHelper {
    protected static Random rand = new Random();

    /**
     * An array of all valid URI schemes as of 2016-10-17.
     * http://www.iana.org/assignments/uri-schemes/uri-schemes.xhtml
     */
    protected static final String[] VALID_SCHEMES = {"http", "https",
        "ftp", "aaa", "aaas", "about", "acap", "acct", "acr", "adiumxtra",
        "afp", "afs", "aim", "appdata", "apt", "attachment", "aw", "barion",
        "beshare", "bitcoin", "blob", "bolo", "browserext", "callto", "cap",
        "chrome", "chrome-extension", "cid", "coap", "coaps",
        "com-eventbrite-attendee", "content", "crid", "cvs", "data", "dav",
        "dict", "dis", "dlna-playcontainer", "dlna-playsingle", "dns", "dntp",
        "dtn", "dvb", "ed2k", "example", "facetime", "fax", "feed",
        "feedready", "file", "filesystem", "finger", "fish", "ftp", "geo",
        "gg", "git", "gizmoproject", "go", "gopher", "gtalk", "h323", "ham",
        "hcp", "http", "https", "iax", "icap", "icon", "im", "imap", "info",
        "iotdisco", "ipn", "ipp", "ipps", "irc", "irc6", "ircs", "iris",
        "iris.beep", "iris.lwz", "iris.xpc", "iris.xpcs", "isostore", "itms",
        "jabber", "jar", "jms", "keyparc", "lastfm", "ldap", "ldaps", "lvlt",
        "magnet", "mailserver", "mailto", "maps", "market", "message", "mid",
        "mms", "modem", "ms-access", "ms-browser-extension", "ms-drive-to",
        "ms-enrollment", "ms-excel", "ms-gamebarservices", "ms-getoffice",
        "ms-help", "ms-infopath", "ms-media-stream-id", "ms-project",
        "ms-powerpoint", "ms-publisher", "ms-search-repair",
        "ms-secondary-screen-controller", "ms-secondary-screen-setup",
        "ms-settings", "ms-settings-airplanemode", "ms-settings-bluetooth",
        "ms-settings-camera", "ms-settings-cellular",
        "ms-settings-cloudstorage", "ms-settings-connectabledevices",
        "ms-settings-displays-topology", "ms-settings-emailandaccounts",
        "ms-settings-language", "ms-settings-location", "ms-settings-lock",
        "ms-settings-nfctransactions", "ms-settings-notifications",
        "ms-settings-power", "ms-settings-privacy", "ms-settings-proximity",
        "ms-settings-screenrotation", "ms-settings-wifi",
        "ms-settings-workplace", "ms-spd", "ms-transit-to",
        "ms-virtualtouchpad", "ms-visio", "ms-walk-to", "ms-word", "msnim",
        "msrp", "msrps", "mtqp", "mumble", "mupdate", "mvn", "news", "nfs",
        "ni", "nih", "nntp", "notes", "ocf", "oid", "opaquelocktoken", "pack",
        "palm", "paparazzi", "pkcs11", "platform", "pop", "pres", "prospero",
        "proxy", "psyc", "qb", "query", "redis", "rediss", "reload", "res",
        "resource", "rmi", "rsync", "rtmfp", "rtmp", "rtsp", "rtsps", "rtspu",
        "secondlife", "service", "session", "sftp", "sgn", "shttp", "sieve",
        "sip", "sips", "skype", "smb", "sms", "smtp", "snews", "snmp",
        "soap.beep", "soap.beeps", "soldat", "spotify", "ssh", "steam", "stun",
        "stuns", "submit", "svn", "tag", "teamspeak", "tel", "teliaeid",
        "telnet", "tftp", "things", "thismessage", "tip", "tn3270", "tool",
        "turn", "turns", "tv", "udp", "unreal", "urn", "ut2004", "v-event",
        "vemmi", "ventrilo", "videotex", "vnc", "view-source", "wais",
        "webcal", "wpid", "ws", "wss", "wtai", "wyciwyg", "xcon",
        "xcon-userid", "xfire", "xmlrpc.beep", "xmlrpc.beeps", "xmpp", "xri",
        "ymsgr", "z39.50", "z39.50r", "z39.50s"};

    /**
     * An array of all valid ascii TLDs as of 2016-11-25.
     * http://www.iana.org/domains/root/db
     */
    protected static final String[] VALID_ASCII_TLDS = {
        /* infrastructure */
        "arpa",
        /* sponsored */
        "aero", "asia", "cat", "coop", "edu", "gov", "int", "jobs",
        "mil", "mobi", "museum", "post", "tel", "travel", "xxx",
        /* restricted */
        "biz", "name", "pro",
        /* generic */
        "aaa", "aarp", "abarth", "abb", "abbott", "abbvie", "abc",
        "able", "abogado", "abudhabi", "academy", "accenture", "accountant",
        "accountants", "aco", "active", "actor", "adac", "ads", "adult", "aeg",
        "aetna", "afamilycompany", "afl", "agakhan", "agency", "aig", "aigo",
        "airbus", "airforce", "airtel", "akdn", "alfaromeo", "alibaba",
        "alipay", "allfinanz", "allstate", "ally", "alsace", "alstom",
        "americanexpress", "americanfamily", "amex", "amfam", "amica",
        "amsterdam", "analytics", "android", "anquan", "anz", "aol",
        "apartments", "app", "apple", "aquarelle", "aramco", "archi", "army",
        "art", "arte", "asda", "associates", "athleta", "attorney", "auction",
        "audi", "audible", "audio", "auspost", "author", "auto", "autos",
        "avianca", "aws", "axa", "azure", "baby", "baidu", "banamex",
        "bananarepublic", "band", "bank", "bar", "barcelona", "barclaycard",
        "barclays", "barefoot", "bargains", "baseball", "basketball",
        "bauhaus", "bayern", "bbc", "bbt", "bbva", "bcg", "bcn", "beats",
        "beauty", "beer", "bentley", "berlin", "best", "bestbuy", "bet",
        "bharti", "bible", "bid", "bike", "bing", "bingo", "bio", "black",
        "blackfriday", "blanco", "blockbuster", "blog", "bloomberg", "blue",
        "bms", "bmw", "bnl", "bnpparibas", "boats", "boehringer", "bofa",
        "bom", "bond", "boo", "book", "booking", "boots", "bosch", "bostik",
        "bot", "boutique", "box", "bradesco", "bridgestone", "broadway",
        "broker", "brother", "brussels", "budapest", "bugatti", "build",
        "builders", "business", "buy", "buzz", "bzh", "cab", "cafe", "cal",
        "call", "calvinklein", "cam", "camera", "camp", "cancerresearch",
        "canon", "capetown", "capital", "capitalone", "car", "caravan",
        "cards", "care", "career", "careers", "cars", "cartier", "casa",
        "case", "caseih", "cash", "casino", "catering", "cba", "cbn", "cbre",
        "cbs", "ceb", "center", "ceo", "cern", "cfa", "cfd", "chanel",
        "channel", "chase", "chat", "cheap", "chintai", "chloe", "christmas",
        "chrome", "chrysler", "church", "cipriani", "circle", "cisco",
        "citadel", "citi", "citic", "city", "cityeats", "claims", "cleaning",
        "click", "clinic", "clinique", "clothing", "cloud", "club", "clubmed",
        "coach", "codes", "coffee", "college", "cologne", "com", "comcast",
        "commbank", "community", "company", "compare", "computer", "comsec",
        "condos", "construction", "consulting", "contact", "contractors",
        "cooking", "cookingchannel", "cool", "corsica", "country", "coupon",
        "coupons", "courses", "credit", "creditcard", "creditunion", "cricket",
        "crown", "crs", "cruise", "cruises", "csc", "cuisinella", "cymru",
        "cyou", "dabur", "dad", "dance", "date", "dating", "datsun", "day",
        "dclk", "dds", "deal", "dealer", "deals", "degree", "delivery", "dell",
        "deloitte", "delta", "democrat", "dental", "dentist", "desi", "design",
        "dev", "dhl", "diamonds", "diet", "digital", "direct", "directory",
        "discount", "discover", "dish", "diy", "dnp", "docs", "doctor",
        "dodge", "dog", "doha", "domains", "doosan", "dot", "download",
        "drive", "dtv", "dubai", "duck", "dunlop", "duns", "dupont", "durban",
        "dvag", "dvr", "earth", "eat", "eco", "edeka", "education", "email",
        "emerck", "energy", "engineer", "engineering", "enterprises", "epost",
        "epson", "equipment", "ericsson", "erni", "esq", "estate", "esurance",
        "eurovision", "eus", "events", "everbank", "exchange", "expert",
        "exposed", "express", "extraspace", "fage", "fail", "fairwinds",
        "faith", "family", "fan", "fans", "farm", "farmers", "fashion", "fast",
        "fedex", "feedback", "ferrari", "ferrero", "fiat", "fidelity", "fido",
        "film", "final", "finance", "financial", "fire", "firestone",
        "firmdale", "fish", "fishing", "fit", "fitness", "flickr", "flights",
        "flir", "florist", "flowers", "flsmidth", "fly", "foo", "food",
        "foodnetwork", "football", "ford", "forex", "forsale", "forum",
        "foundation", "fox", "free", "fresenius", "frl", "frogans",
        "frontdoor", "frontier", "ftr", "fujitsu", "fujixerox", "fund",
        "furniture", "futbol", "fyi", "gal", "gallery", "gallo", "gallup",
        "game", "games", "gap", "garden", "gbiz", "gdn", "gea", "gent",
        "genting", "george", "ggee", "gift", "gifts", "gives", "giving",
        "glade", "glass", "gle", "global", "globo", "gmail", "gmbh", "gmo",
        "gmx", "godaddy", "gold", "goldpoint", "golf", "goo", "goodhands",
        "goodyear", "goog", "google", "gop", "got", "grainger", "graphics",
        "gratis", "green", "gripe", "group", "guardian", "gucci", "guge",
        "guide", "guitars", "guru", "hamburg", "hangout", "haus", "hbo",
        "hdfc", "hdfcbank", "health", "healthcare", "help", "helsinki", "here",
        "hermes", "hgtv", "hiphop", "hisamitsu", "hitachi", "hiv", "hkt",
        "hockey", "holdings", "holiday", "homedepot", "homegoods", "homes",
        "homesense", "honda", "honeywell", "horse", "host", "hosting", "hot",
        "hoteles", "hotmail", "house", "how", "hsbc", "htc", "hughes", "hyatt",
        "hyundai", "ibm", "icbc", "ice", "icu", "ieee", "ifm", "iinet",
        "ikano", "imamat", "imdb", "immo", "immobilien", "industries",
        "infiniti", "info", "ing", "ink", "institute", "insurance", "insure",
        "intel", "international", "intuit", "investments", "ipiranga", "irish",
        "iselect", "ismaili", "ist", "istanbul", "itau", "itv", "iveco", "iwc",
        "jaguar", "java", "jcb", "jcp", "jeep", "jetzt", "jewelry", "jio",
        "jlc", "jll", "jmp", "jnj", "joburg", "jot", "joy", "jpmorgan", "jprs",
        "juegos", "juniper", "kaufen", "kddi", "kerryhotels", "kerrylogistics",
        "kerryproperties", "kfh", "kia", "kim", "kinder", "kindle", "kitchen",
        "kiwi", "koeln", "komatsu", "kosher", "kpmg", "kpn", "krd", "kred",
        "kuokgroup", "kyoto", "lacaixa", "ladbrokes", "lamborghini", "lamer",
        "lancaster", "lancia", "lancome", "land", "landrover", "lanxess",
        "lasalle", "lat", "latino", "latrobe", "law", "lawyer", "lds", "lease",
        "leclerc", "lefrak", "legal", "lego", "lexus", "lgbt", "liaison",
        "lidl", "life", "lifeinsurance", "lifestyle", "lighting", "like",
        "lilly", "limited", "limo", "lincoln", "linde", "link", "lipsy",
        "live", "living", "lixil", "loan", "loans", "locker", "locus", "loft",
        "lol", "london", "lotte", "lotto", "love", "lpl", "lplfinancial",
        "ltd", "ltda", "lundbeck", "lupin", "luxe", "luxury", "macys",
        "madrid", "maif", "maison", "makeup", "man", "management", "mango",
        "market", "marketing", "markets", "marriott", "marshalls", "maserati",
        "mattel", "mba", "mcd", "mcdonalds", "mckinsey", "med", "media",
        "meet", "melbourne", "meme", "memorial", "men", "menu", "meo",
        "metlife", "miami", "microsoft", "mini", "mint", "mit", "mitsubishi",
        "mlb", "mls", "mma", "mobily", "moda", "moe", "moi", "mom", "monash",
        "money", "monster", "montblanc", "mopar", "mormon", "mortgage",
        "moscow", "moto", "motorcycles", "mov", "movie", "movistar", "msd",
        "mtn", "mtpc", "mtr", "mutual", "mutuelle", "nab", "nadex", "nagoya",
        "nationwide", "natura", "navy", "nba", "nec", "net", "netbank",
        "netflix", "network", "neustar", "new", "newholland", "news", "next",
        "nextdirect", "nexus", "nfl", "ngo", "nhk", "nico", "nike", "nikon",
        "ninja", "nissan", "nissay", "nokia", "northwesternmutual", "norton",
        "now", "nowruz", "nowtv", "nra", "nrw", "ntt", "nyc", "obi",
        "observer", "off", "office", "okinawa", "olayan", "olayangroup",
        "oldnavy", "ollo", "omega", "one", "ong", "onl", "online",
        "onyourside", "ooo", "open", "oracle", "orange", "org", "organic",
        "orientexpress", "origins", "osaka", "otsuka", "ott", "ovh", "page",
        "pamperedchef", "panasonic", "panerai", "paris", "pars", "partners",
        "parts", "party", "passagens", "pay", "pccw", "pet", "pfizer",
        "pharmacy", "philips", "photo", "photography", "photos", "physio",
        "piaget", "pics", "pictet", "pictures", "pid", "pin", "ping", "pink",
        "pioneer", "pizza", "place", "play", "playstation", "plumbing", "plus",
        "pnc", "pohl", "poker", "politie", "porn", "pramerica", "praxi",
        "press", "prime", "prod", "productions", "prof", "progressive",
        "promo", "properties", "property", "protection", "pru", "prudential",
        "pub", "pwc", "qpon", "quebec", "quest", "qvc", "racing", "radio",
        "raid", "read", "realestate", "realtor", "realty", "recipes", "red",
        "redstone", "redumbrella", "rehab", "reise", "reisen", "reit",
        "reliance", "ren", "rent", "rentals", "repair", "report", "republican",
        "rest", "restaurant", "review", "reviews", "rexroth", "rich",
        "richardli", "ricoh", "rightathome", "ril", "rio", "rip", "rmit",
        "rocher", "rocks", "rodeo", "rogers", "room", "rsvp", "ruhr", "run",
        "rwe", "ryukyu", "saarland", "safe", "safety", "sakura", "sale",
        "salon", "samsclub", "samsung", "sandvik", "sandvikcoromant", "sanofi",
        "sap", "sapo", "sarl", "sas", "save", "saxo", "sbi", "sbs", "sca",
        "scb", "schaeffler", "schmidt", "scholarships", "school", "schule",
        "schwarz", "science", "scjohnson", "scor", "scot", "seat", "secure",
        "security", "seek", "select", "sener", "services", "ses", "seven",
        "sew", "sex", "sexy", "sfr", "shangrila", "sharp", "shaw", "shell",
        "shia", "shiksha", "shoes", "shop", "shopping", "shouji", "show",
        "showtime", "shriram", "silk", "sina", "singles", "site", "ski",
        "skin", "sky", "skype", "sling", "smart", "smile", "sncf", "soccer",
        "social", "softbank", "software", "sohu", "solar", "solutions", "song",
        "sony", "soy", "space", "spiegel", "spot", "spreadbetting", "srl",
        "srt", "stada", "staples", "star", "starhub", "statebank", "statefarm",
        "statoil", "stc", "stcgroup", "stockholm", "storage", "store",
        "stream", "studio", "study", "style", "sucks", "supplies", "supply",
        "support", "surf", "surgery", "suzuki", "swatch", "swiftcover",
        "swiss", "sydney", "symantec", "systems", "tab", "taipei", "talk",
        "taobao", "target", "tatamotors", "tatar", "tattoo", "tax", "taxi",
        "tci", "tdk", "team", "tech", "technology", "telecity", "telefonica",
        "temasek", "tennis", "teva", "thd", "theater", "theatre", "tiaa",
        "tickets", "tienda", "tiffany", "tips", "tires", "tirol", "tjmaxx",
        "tjx", "tkmaxx", "tmall", "today", "tokyo", "tools", "top", "toray",
        "toshiba", "total", "tours", "town", "toyota", "toys", "trade",
        "trading", "training", "travelchannel", "travelers",
        "travelersinsurance", "trust", "trv", "tube", "tui", "tunes", "tushu",
        "tvs", "ubank", "ubs", "uconnect", "unicom", "university", "uno",
        "uol", "ups", "vacations", "vana", "vanguard", "vegas", "ventures",
        "verisign", "vermgensberater", "vermgensberatung", "versicherung",
        "vet", "viajes", "video", "vig", "viking", "villas", "vin", "vip",
        "virgin", "visa", "vision", "vista", "vistaprint", "viva", "vivo",
        "vlaanderen", "vodka", "volkswagen", "volvo", "vote", "voting", "voto",
        "voyage", "vuelos", "wales", "walmart", "walter", "wang", "wanggou",
        "warman", "watch", "watches", "weather", "weatherchannel", "webcam",
        "weber", "website", "wed", "wedding", "weibo", "weir", "whoswho",
        "wien", "wiki", "williamhill", "win", "windows", "wine", "winners",
        "wme", "wolterskluwer", "woodside", "work", "works", "world", "wow",
        "wtc", "wtf", "xbox", "xerox", "xfinity", "xihuan", "xin", "xperia",
        "xyz", "yachts", "yahoo", "yamaxun", "yandex", "yodobashi", "yoga",
        "yokohama", "you", "youtube", "yun", "zappos", "zara", "zero", "zip",
        "zippo", "zone", "zuerich",
        /* country codes */
        "ac", "ad", "ae", "af", "ag", "ai", "al", "am", "an", "ao",
        "aq", "ar", "as", "at", "au", "aw", "ax", "az", "ba", "bb", "bd", "be",
        "bf", "bg", "bh", "bi", "bj", "bl", "bm", "bn", "bo", "bq", "br", "bs",
        "bt", "bv", "bw", "by", "bz", "ca", "cc", "cd", "cf", "cg", "ch", "ci",
        "ck", "cl", "cm", "cn", "co", "cr", "cu", "cv", "cw", "cx", "cy", "cz",
        "de", "dj", "dk", "dm", "do", "dz", "ec", "ee", "eg", "eh", "er", "es",
        "et", "eu", "fi", "fj", "fk", "fm", "fo", "fr", "ga", "gb", "gd", "ge",
        "gf", "gg", "gh", "gi", "gl", "gm", "gn", "gp", "gq", "gr", "gs", "gt",
        "gu", "gw", "gy", "hk", "hm", "hn", "hr", "ht", "hu", "id", "ie", "il",
        "im", "in", "io", "iq", "ir", "is", "it", "je", "jm", "jo", "jp", "ke",
        "kg", "kh", "ki", "km", "kn", "kp", "kr", "kw", "ky", "kz", "la", "lb",
        "lc", "li", "lk", "lr", "ls", "lt", "lu", "lv", "ly", "ma", "mc", "md",
        "me", "mf", "mg", "mh", "mk", "ml", "mm", "mn", "mo", "mp", "mq", "mr",
        "ms", "mt", "mu", "mv", "mw", "mx", "my", "mz", "na", "nc", "ne", "nf",
        "ng", "ni", "nl", "no", "np", "nr", "nu", "nz", "om", "pa", "pe", "pf",
        "pg", "ph", "pk", "pl", "pm", "pn", "pr", "ps", "pt", "pw", "py", "qa",
        "re", "ro", "rs", "ru", "rw", "sa", "sb", "sc", "sd", "se", "sg", "sh",
        "si", "sj", "sk", "sl", "sm", "sn", "so", "sr", "ss", "st", "su", "sv",
        "sx", "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tl", "tm",
        "tn", "to", "tp", "tr", "tt", "tv", "tw", "tz", "ua", "ug", "uk", "um",
        "us", "uy", "uz", "va", "vc", "ve", "vg", "vi", "vn", "vu", "wf", "ws",
        "ye", "yt", "za", "zm", "zw"
    };

    /**
     * All valid non-ascii TLDs for left-to-right languages in Punycode.
     */
    protected static final String[] VALID_NON_ASCII_TLDS = {
        /* generic */
        "xn--qcka1pmc", "xn--gckr3f0f", "xn--tckwe", "xn--cck2b3b",
        "xn--1ck2e1b", "xn--bck1b9a5dre4c", "xn--eckvdtc9d", "xn--q9jyb4c",
        "xn--t60b56a", "xn--mk1bu44c", "xn--cg4bki", "xn--rhqv96g",
        "xn--fiq64b", "xn--fiq228c5hs", "xn--vhquv", "xn--1qqw23a",
        "xn--vuq861b", "xn--nyqy26a", "xn--45q11c", "xn--55qx5d",
        "xn--55qw42g", "xn--czru2d", "xn--czrs0t", "xn--czr694b",
        "xn--w4rs40l", "xn--w4r85el8fhu5dnra", "xn--3ds443g",
        "xn--3oq18vl8pn36a", "xn--pssy2u", "xn--fjq720a", "xn--fct429k",
        "xn--estv75g", "xn--xhq521b", "xn--9krt00a", "xn--30rr7y",
        "xn--6qq986b3xl", "xn--kput3i", "xn--kpu716f", "xn--zfr164b",
        "xn--mxtq1m", "xn--efvy88h", "xn--9et52u", "xn--rovu88b", "xn--nqv7f",
        "xn--b4w605ferd", "xn--unup4y", "xn--3pxu8k", "xn--pbt977c",
        "xn--6frz82g", "xn--nqv7fs00ema", "xn--ses554g", "xn--hxt814e",
        "xn--5tzm5g", "xn--io0a7i", "xn--8y0a063a", "xn--jlq61u9w7b",
        "xn--flw351e", "xn--g2xx48c", "xn--gk3at1e", "xn--3bst00m",
        "xn--fzys8d69uvgm", "xn--kcrx77d1x4a", "xn--jvr189m", "xn--imr513n",
        "xn--5su34j936bgsg", "xn--42c2d9a", "xn--11b4c3d", "xn--c2br7g",
        "xn--i1b6b1a6a2e", "xn--d1acj3b", "xn--j1aef", "xn--80adxhks",
        "xn--80asehdb", "xn--c1avg", "xn--p1acf", "xn--80aswg",
        /* country codes */
        "xn--qxam", "xn--90ae", "xn--90ais", "xn--e1a4c",
        "xn--80ao21a", "xn--d1alf", "xn--l1acc", "xn--p1ai", "xn--90a3ac",
        "xn--j1amh", "xn--y9a3aq", "xn--node", "xn--h2brj9c", "xn--h2breg3eve",
        "xn--h2brj9c8c", "xn--54b7fta0cc", "xn--45brj9c", "xn--45br5cyl",
        "xn--s9brj9c", "xn--gecrj9c", "xn--3hcrj9c", "xn--xkc2dl3a5ee0h",
        "xn--xkc2al3hye2a", "xn--clchc0ea0b2g2a9gcd", "xn--fpcrj9c3d",
        "xn--2scrj9c", "xn--rvc1e0am3e", "xn--fzc2c9e2c", "xn--o3cw4h",
        "xn--3e0b707e", "xn--fiqs8s", "xn--fiqz9s", "xn--kprw13d",
        "xn--kpry57d", "xn--yfro4i67o", "xn--mix891f", "xn--j6w193g"
    };

    protected static final String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                                + "abcdefghijklmnopqrstuvwxyz"
                                                + "0123456789";
    protected static final String URL_SAFE_SYMBOLS = "-_.!*'()";
    protected static final int MAX_USERNAME_LENGTH = 30;
    protected static final int MAX_PASSWORD_LENGTH = 30;
    protected static final int MAX_LABEL_LENGTH = 63; // per RFC2181
    protected static final int MAX_DOMAIN_LENGTH = 253; // per RFC1034
    protected static final int MAX_DOMAIN_DEPTH = 127;
    protected static final int LONGEST_TLD = 24; // as of 11/25/2016
    protected static final int MAX_PORT_NUM = 65535; // 16-bit unsigned int
    protected static final int MAX_PATH_LENGTH = 255; // arbitrary
    protected static final int MAX_QUERY_LENGTH = 1024; // arbitrary
    protected static final int MAX_FRAGMENT_LENGTH = 256; // arbitrary


    /**
     * Gets a random valid scheme.
     */
    protected static void getRandomValidScheme(StringBuilder sb) {
        int index = rand.nextInt(VALID_SCHEMES.length);
        sb.append(VALID_SCHEMES[index]);
        sb.append("://");
    }

    /**
     * Gets a random valid username, randomly with a password.
     */
    protected static void getRandomValidUserInfo(StringBuilder sb) {
        int len = rand.nextInt(MAX_USERNAME_LENGTH) + 1;
        int index = 0;
        for (int i = 0; i < len; ++i) {
            index = rand.nextInt(ALPHA_NUMERIC.length());
            sb.append(ALPHA_NUMERIC.charAt(index));
        }

        // Decide whether to include password
        int die = rand.nextInt(2);
        if (die == 0) {
            sb.append(":");
            String validChars = ALPHA_NUMERIC + URL_SAFE_SYMBOLS;
            len = rand.nextInt(MAX_PASSWORD_LENGTH) + 1;
            for (int i = 0; i < len; ++i) {
                index = rand.nextInt(validChars.length());
                sb.append(validChars.charAt(index));
            }
        }
        sb.append("@");
    }

    protected static void getRandomValidHostname(StringBuilder sb) {
        // Generate up to (MAX_DOMAIN_DEPTH - 1) subdomain labels
        String labelChars = ALPHA_NUMERIC + "-";
        int subdomains = rand.nextInt(MAX_DOMAIN_DEPTH - 1) + 1;
        int totalLength = 0;
        int index = 0;
        for (int i = 0; i < subdomains; ++i) {
            int labelLen = rand.nextInt(MAX_LABEL_LENGTH) + 1;
            totalLength += labelLen + 1;

            // Stop adding new labels if max URL length would be exceeded
            if (totalLength > MAX_DOMAIN_LENGTH - (LONGEST_TLD + 1))
                break;

            for (int j = 0; j < labelLen; ++j) {
                index = rand.nextInt(labelChars.length());
                sb.append(labelChars.charAt(index));
            }
            sb.append(".");
        }

        // Add a random top-level domain to the end
        index = rand.nextInt(VALID_ASCII_TLDS.length
                + VALID_NON_ASCII_TLDS.length);
        if (index < VALID_ASCII_TLDS.length)
            sb.append(VALID_ASCII_TLDS[index]);
        else
            sb.append(VALID_NON_ASCII_TLDS[index - VALID_ASCII_TLDS.length]);
    }

    protected static void getRandomValidPath(StringBuilder sb) {
        String pathChars = ALPHA_NUMERIC + URL_SAFE_SYMBOLS + "/";
        int pathLength = rand.nextInt(MAX_PATH_LENGTH);
        int index = 0;
        for (int i = 0; i < pathLength; ++i) {
            index = rand.nextInt(pathChars.length() + 10);
            // generate random escaped char if num>=pathChars.length()
            if (index < pathChars.length())
                sb.append(pathChars.charAt(index));
            else {
                sb.append(String.format("%%%2x", rand.nextInt(256 - 32) + 32));
            }

        }
    }

    protected static void getRandomValidQueryString(StringBuilder sb) {
        sb.append("?");
        String queryChars = ALPHA_NUMERIC + URL_SAFE_SYMBOLS + "&=;";
        int queryLength = rand.nextInt(MAX_QUERY_LENGTH);
        for (int i = 0; i < queryLength; ++i) {
            int index = rand.nextInt(queryChars.length() + 10);
            // generate random escaped char if num>=pathChars.length()
            if (index < queryChars.length())
                sb.append(queryChars.charAt(index));
            else {
                sb.append(String.format("%%%2x", rand.nextInt(256 - 32) + 32));
            }
        }
    }

    protected static void getRandomValidFragment(StringBuilder sb) {
        sb.append("#");
        String fragmentChars = ALPHA_NUMERIC + URL_SAFE_SYMBOLS;
        int fragLength = rand.nextInt(MAX_FRAGMENT_LENGTH);
        int index = 0;
        for (int i = 0; i < fragLength; ++i) {
            index = rand.nextInt(fragmentChars.length());
            sb.append(fragmentChars.charAt(index));
        }
    }

    /**
     * Generates a URL comprised of randomly generated valid strings.
     * Always includes the host part.
     * Arguments specify whether to include the following parts:
     *  - scheme
     *  - User info
     *  - port
     *  - path
     *  - query
     *  - fragment
     */
    public static String getRandomValidUrl(
            boolean scheme,
            boolean userinfo, 
            boolean port,
            boolean path, 
            boolean query, 
            boolean fragment
            ) {
        StringBuilder sb = new StringBuilder();

        if (scheme) {
            // Include a random valid scheme
            getRandomValidScheme(sb);
        }

        if (userinfo) {
            // Include a random valid username/password
            getRandomValidUserInfo(sb);
        }

        // Get random hostname
        getRandomValidHostname(sb);

        if (port) {
            sb.append(":" + Integer.toString(rand.nextInt(MAX_PORT_NUM + 1)));
        }

        if (path || query || fragment) {
            sb.append("/");
        }

        if (path) {
            getRandomValidPath(sb);
        }

        if (query) {
            getRandomValidQueryString(sb);
        }
        
        if (fragment) {
            getRandomValidFragment(sb);
        }

        return sb.toString();
    }
}
