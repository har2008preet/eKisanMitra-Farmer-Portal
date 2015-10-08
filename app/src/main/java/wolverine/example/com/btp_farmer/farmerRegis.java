package wolverine.example.com.btp_farmer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;
import static wolverine.example.com.btp_farmer.CommonUtilities.SENDER_ID;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.HashMap;
import java.util.Map;

import app.AppController;


public class farmerRegis extends ActionBarActivity {

    EditText firstName,lastName,pinCode,address,pnumber;
    Spinner state,district;
    private ProgressDialog pDialog;
    Button register;
        GoogleCloudMessaging gcm;
        String regid;
        Context context;
    private GCMClientManager pushClientManager;

    String PROJECT_NUMBER = "921078016964";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_regis);

            register = (Button)findViewById(R.id.btnRegister);

        firstName = (EditText)findViewById(R.id.etfirstName);
        lastName = (EditText)findViewById(R.id.etlastName);
        pinCode = (EditText)findViewById(R.id.etpin);
        address = (EditText)findViewById(R.id.etaddress);
        pnumber = (EditText)findViewById(R.id.etphoneNumber);

        state = (Spinner)findViewById(R.id.state);
        district = (Spinner)findViewById(R.id.district);

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String[] ChoosefromHere=new String[]{"Choose from here"};
                String[] AndhraPradesh=new String[]{"Choose from here","Nellore",
                        "Cuddapah",
                        "Kurnool",
                        "West Godavari",
                        "Srikakulam",
                        "Anantpur",
                        "Chittor",
                        "east Godavari",
                        "Guntur",
                        "Krishna",
                        "Prakasam",
                        "Vishakapatnam",
                        "Vizianagaram"};
                String[] ArunachalPradesh=new String[]{"Choose from here","Diban Valley( Anini Valley)",
                        "East Kameng Seppa",
                        "Itanagar",
                        "East Siang(Passighat)",
                        "Lohit(Tezu)",
                        "Lower Subansiri(Ziro)",
                        "Khonsa",
                        "Roin",
                        "Tawang",
                        "Changalang",
                        "Dibang Valley",
                        "Daporijo",
                        "Bomdila",
                        "Alog( West Siang )"};
                String[] Assam=new String[]{"Choose from here","Karimganj",
                        "Darrang",
                        "Dibrugarh",
                        "Morigaon",
                        "Tinsukia",
                        "Bongaigaon",
                        "Nalbari",
                        "Kokrajhar",
                        "Kamrup",
                        "Karbi-Anglong",
                        "Nagaon",
                        "N.C.Hills",
                        "Dhemaji",
                        "Hailakandi",
                        "Lakhimpur",
                        "Sonitpur",
                        "Dhubri",
                        "Goalpara",
                        "Barpeta",
                        "Golaghat",
                        "Jorhat",
                        "Sibsagar",
                        "Cachar"};
                String[] Bihar=new String[]{"Choose from here","Muzaffarpur",
                        "Jehanabad",
                        "Gaya",
                        "Patna",
                        "Saran( Chapra )",
                        "Darbhanga",
                        "Saharsa",
                        "Purnea",
                        "Bhagalpur",
                        "Munger",
                        "Aurangabad",
                        "Bhojpur(Arah)",
                        "Begusarai",
                        "East Champaran",
                        "Gopalganj",
                        "Jamui",
                        "Katihar",
                        "Khagaria",
                        "Madhepura",
                        "Madhubani",
                        "Nalanda",
                        "Nawada",
                        "Rohtas(Sasaram)",
                        "Samastipur",
                        "Sitamarhi",
                        "Siwan",
                        "Vaishali(Hajipur)",
                        "West Champaran",
                        "Kishanganj",
                        "Araria",
                        "Bhabua",
                        "Banka",
                        "Buxar",
                        "Supaul",
                        "Sekhpura",
                        "Seohar",
                        "Lakhisarai"};
                String[] Chhattisgarh=new String[]{"Choose from here","Bastar",
                        "Bilaspur",
                        "Dantewada",
                        "Dhamtari",
                        "Durg",
                        "Janjgir-Champa",
                        "Jashpur",
                        "Kanker",
                        "Kawardha",
                        "Korba",
                        "Koriya",
                        "Mahasamund",
                        "Raigarh",
                        "Rajnandgaon",
                        "Surguja",
                        "Raipur"};
                String[] Chandigarh=new String[]{"Choose from here","Chandigarh"};
                String[] Delhi=new String[]{"Choose from here","New Delhi",
                        "Central",
                        "North",
                        "North West",
                        "West",
                        "South West",
                        "North",
                        "North East"};
                String[] Goa=new String[]{"Choose from here","North Goa",
                        "South Goa"};
                String[] Gujarat=new String[]{"Choose from here","Panchmahals",
                        "Vadodara",
                        "Amrela",
                        "Kheda",
                        "Ahmedabad",
                        "Valsad",
                        "Junagadh",
                        "Mehsana",
                        "Banaskantha",
                        "Gandhinagar",
                        "Bharuch",
                        "Dangs",
                        "Jamnagar",
                        "Rajkot",
                        "Surat",
                        "Sabarkantha",
                        "Kutch",
                        "Bhavnagar",
                        "Surendranagar",
                        "Navsari",
                        "Anand",
                        "Narmada",
                        "Patan",
                        "Porbander",
                        "Dahod"};
                String[] Haryana=new String[]{"Choose from here","Gurgaon",
                        "Rohtak",
                        "Ambala",
                        "Bhiwani",
                        "Faridabad",
                        "Hissar",
                        "Jind",
                        "Kaithal",
                        "Karnal",
                        "Kurukshetra",
                        "Mahendragarh",
                        "Panchkula",
                        "Panipat",
                        "Sonipat",
                        "Yamunanagar",
                        "Sirsa",
                        "Rewari",
                        "Jhanjhar",
                        "Fatehabad"};
                String[] HimachalPradesh=new String[]{"Choose from here","Sirmour",
                        "Hamirpur",
                        "Kullu",
                        "Solan",
                        "Mandi",
                        "Chamba",
                        "Bilaspur",
                        "Kangra",
                        "Kinnaur",
                        "Lahaul-Spiti",
                        "Shimla",
                        "Una"};
                String[] JammuandKashmir=new String[]{"Choose from here","Kathua",
                        "Badgan",
                        "Poonch",
                        "Rajauri",
                        "Baramula",
                        "Doda",
                        "Udhampur",
                        "Jammu",
                        "Kupwara",
                        "Pulwama",
                        "Anantnag",
                        "Srinagar",
                        "Leh",
                        "Kargil"};
                String[] Jharkhand=new String[]{"Choose from here","Deoghar",
                        "Dhanbad",
                        "Giridih",
                        "Godda",
                        "Gumla",
                        "Hazaribagh",
                        "Lohardaga",
                        "Palamu",
                        "Ranchi",
                        "Dumka",
                        "Chaibasa(West Singhbhum)",
                        "Jamshedpur(East Singhbhum)",
                        "Bokaro",
                        "Chatra",
                        "Garhwa",
                        "Koderma",
                        "Pakur",
                        "Sahebganj",
                        "Simdega",
                        "Latehar",
                        "Saraikela",
                        "Jamtara"};
                String[] Karnataka=new String[]{"Choose from here","Mysore",
                        "Gulberga",
                        "Chitradurga",
                        "Kolar",
                        "Bijapur",
                        "Dakshina Kannada",
                        "Raichur",
                        "Bellary",
                        "Belgaum",
                        "Hassan",
                        "Dharwad",
                        "Bangalore Rural",
                        "Shimoga",
                        "Mandya",
                        "Chickmagalur",
                        "Bangalore Urban",
                        "Madikeri",
                        "Tumkur",
                        "Bidar",
                        "Karwar",
                        "Udupi",
                        "Davanagare",
                        "Chamrajnagar",
                        "Koppal",
                        "Haveri",
                        "Gadak",
                        "Yadgir"};
                String[] Kerala=new String[]{"Choose from here","Kozhikode",
                        "Kasaragod",
                        "Idukki",
                        "Ernakulam",
                        "Cannanore",
                        "Mallapuram",
                        "Palghat",
                        "Pathanamthitta",
                        "Quilon",
                        "Trichur",
                        "Wayanad",
                        "Trivandrum",
                        "Kottayam",
                        "Alapuzzha"};
                String[] MadhyaPradesh=new String[]{"Choose from here","Sindi",
                        "Vidisha",
                        "Jabalpur",
                        "Bhopal",
                        "Hoshangabad",
                        "Indore",
                        "Rewa",
                        "Satna",
                        "Shahdol",
                        "Chhindwara",
                        "Ratlam",
                        "Balaghat",
                        "Betul",
                        "Bhind",
                        "Mandla",
                        "Chhattarpur",
                        "Damoh",
                        "Datia",
                        "Dewas",
                        "Dhar",
                        "Guna",
                        "Gwalior",
                        "Jhabua",
                        "Sehore",
                        "Mandsaur",
                        "Narsinghpur",
                        "Panna",
                        "Raisen",
                        "Rajgarh",
                        "Sagar",
                        "Seoni",
                        "Morena",
                        "Shivpuri",
                        "Shajapur",
                        "Tikamgarh",
                        "Ujjain",
                        "Khandwa",
                        "Khargone",
                        "Dindori",
                        "Umaria",
                        "Badwani",
                        "Sheopur",
                        "Katni",
                        "Neemuch",
                        "Harda",
                        "Anooppur",
                        "Burhanpur",
                        "Ashoknagar"};
                String[] Maharashtra=new String[]{"Choose from here","Aurangabad",
                        "Bandra(Mumbai Suburban district)",
                        "Nagpur",
                        "Pune",
                        "Akola",
                        "Chandrapur",
                        "Jalgaon",
                        "Parbhani",
                        "Sholapur",
                        "Thane",
                        "Latur",
                        "Mumbai-City",
                        "Buldhana",
                        "Dhule",
                        "Kolhpur",
                        "Nanded",
                        "Raigad",
                        "Amravati",
                        "Nashik",
                        "Wardha",
                        "Ahmednagar",
                        "Beed",
                        "Bhandara",
                        "Gadchiroli",
                        "Jalna",
                        "Osmanabad",
                        "Ratnagiri",
                        "Sangli",
                        "Satara",
                        "Sindudurg",
                        "Yavatmal",
                        "Nandurbar",
                        "Washim",
                        "Gondia",
                        "Hingoli"};
                String[] Manipur=new String[]{"Choose from here","Imphal East",
                        "Imphal West",
                        "Thoubal",
                        "Bishnupur",
                        "Chandel",
                        "Churachandpur",
                        "Senapati",
                        "Ukhrul",
                        "Tamenglong"};
                String[] Meghalaya=new String[]{"Choose from here","Ri-Bhoi District",
                        "South Garo Hills",
                        "East Khasi Hill",
                        "East Garo Hill",
                        "West Garo Hill",
                        "Jaintia Hill",
                        "West Khasi Hill"};
                String[] Mizoram=new String[]{"Choose from here","Luglei District",
                        "Chimtipui District",
                        "Aizawal",
                        "Champhai",
                        "Mamit",
                        "Kolasib",
                        "Serchhip",
                        "Lawngtlai"};
                String[] Nagaland=new String[]{"Choose from here","Wokha",
                        "Phek",
                        "Tuensang",
                        "Mon",
                        "Kohima",
                        "Zunheboto",
                        "Mokokchung",
                        "Dimapur"};
                String[] Orissa=new String[]{"Choose from here","Khurda",
                        "Navaragpur",
                        "Navapada",
                        "Gajapati",
                        "Boudh",
                        "Bhadrak",
                        "Ganjam",
                        "Dhenkanal",
                        "Angul",
                        "Puri",
                        "Cuttak",
                        "Sambalpur",
                        "Kalhandi",
                        "Koraput",
                        "Phulbani",
                        "Balangir",
                        "Bargah",
                        "Deogarh",
                        "Jagatsinghpur",
                        "Jajpur",
                        "Jharsuguda",
                        "Kendrapara",
                        "Malkangiri",
                        "Nayagarh",
                        "Rayagada",
                        "Sonepur",
                        "Balasore",
                        "Mayurbhanj",
                        "Keonjhar",
                        "Sundergarh"};
                String[] Punjab=new String[]{"Choose from here","Sangrur",
                        "Jalandhar",
                        "Ludhiana",
                        "Bhatinda",
                        "Kapurthala",
                        "Patiala",
                        "Amritsar",
                        "Ferozepur",
                        "Fatehgarh Saheb",
                        "Ropar",
                        "Gurdaspur",
                        "Hosiarpur",
                        "Faridkot",
                        "Mansa",
                        "Moga",
                        "Muktsar",
                        "Navansahar"};
                String[] Rajasthan=new String[]{"Choose from here","Jaipur",
                        "Barmer",
                        "Dungarpur",
                        "Jodhpur",
                        "Kota",
                        "Udaipur",
                        "Bikaner",
                        "Dausa",
                        "Bundi",
                        "Sikar",
                        "Tonk",
                        "Jaisalmer",
                        "Nagaur",
                        "Rajsamand",
                        "Banswara",
                        "Bhilwara",
                        "Ajmer",
                        "Alwar",
                        "Bharatpur",
                        "Chittorgarh",
                        "Churu",
                        "Dholpur",
                        "Ganganagar",
                        "Jalor",
                        "Jhalawar",
                        "Jhunjhunu",
                        "Pali",
                        "Sawai Madhopur",
                        "Sirohi",
                        "Baran",
                        "Hanumangarh",
                        "Karauli"};
                String[] Sikkim=new String[]{"Choose from here","East",
                        "South",
                        "West",
                        "North"};
                String[] TamilNadu=new String[]{"Choose from here","Chennai",
                        "Coimbotore",
                        "Cuddalorei",
                        "Dharmapuri",
                        "Dindigul",
                        "Erode",
                        "Kancheepuram",
                        "Kanniyakumari (HQ : Nagercoil)",
                "Karur",
                        "Madurai",
                "Nagapattinam",
                        "Namakkal",
                "Nilgiris (HQ: Udhagamandalam)",
                "Perambalur",
                        "Pudukkottai",
                "Ramanathapuram",
                        "Salem",
                "Sivaganga",
                        "Thanjavur",
                "Theni",
                        "Thoothkudi",
                "Tiruchiorappalli",
                        "Tirunelveli",
                "Tiruvallur",
                        "Tiruvannamalai",
                "Tiruvarur",
                        "Vellore",
                "Villupuram",
                "Virudhunagar"};
                String[] Telangana=new String[]{"Choose from here","Adilabad","Hyderabad","Karimnagar","Khammam","Mahboobnagar","Medak","Nalgonda","Nizamabad","Ranga Reddy","Warangal"};
                String[] Tripura=new String[]{"Choose from here","North District",
                        "South District",
                        "West District",
                        "Dhalai District"};
                String[] UttarPradesh=new String[]{"Choose from here","Allahabad",
                        "Aligarh",
                        "Bareilly",
                        "Gonda",
                        "Hardoi",
                        "Kanpur",
                        "Ghaziabad",
                        "Unnav",
                        "Varanasi",
                        "Faizabad",
                        "Gorakpur",
                        "Jhansi",
                        "Lucknow",
                        "Agra",
                        "Meerut",
                        "Moradabad",
                        "Barabanki",
                        "Mainpuri",
                        "Etawah",
                        "Gazipur",
                        "Etah",
                        "Muzaffar Nagar",
                        "Saharanpur",
                        "Bulandshehar",
                        "Mathura",
                        "Firozabad",
                        "Budaun",
                        "Shahjahanpur",
                        "Pilibhit",
                        "Bijnor",
                        "Rampur",
                        "Kanpur(Nagar)",
                        "Farrukhabad",
                        "Fatehpur",
                        "Pratapgarh",
                        "Jalaun",
                        "Hamirpur",
                        "Lalitpur",
                        "Mirzapur",
                        "Basti",
                        "Deoria",
                        "Raebareili",
                        "Sitapur",
                        "Banda",
                        "Lakhimpur-Khedi",
                        "Bahraich",
                        "Sultanpur",
                        "Mau",
                        "Azamgarh",
                        "Jaunpur",
                        "Balia",
                        "Bhadoi",
                        "Padrauna",
                        "Maharajganj",
                        "Siddharth Nagar",
                        "Sunbhadra",
                        "Mahoba",
                        "Ambedkarnagar",
                        "Gautam Bodda Nagar",
                        "Maha Maya Nagar",
                        "jyotiba Phoole Nagar",
                        "Kaushambi",
                        "Shooji Maharaj",
                        "Chandauli",
                        "Balrampur",
                        "Shravati",
                        "Bagpat",
                        "Kanooj",
                        "Oraiyya",
                        "Sant Kabir Nagar"};
                String[] Uttarakhand=new String[]{"Choose from here","Nainital",
                        "Almora",
                        "Pitoragarh",
                        "Udhamsingh Nagar",
                        "Bageshwar",
                        "Champawat",
                        "Garhwal(Pauri)",
                        "Tehri-Garhwal",
                        "Chamoli( Gopeshwar )",
                        "Uttarkashi",
                        "Dehradun",
                        "Rudraprayag",
                        "Haridwar"};
                String[] WestBengal=new String[]{"Choose from here","Howrah",
                        "Darjeeling",
                        "Medinipur",
                        "Murshidabad",
                        "Coochbehar",
                        "Malda",
                        "Birbhum",
                        "North 24 Parganas",
                        "South 24 Parganas",
                        "Bankura",
                        "Bardhaman",
                        "Jalpaiguri",
                        "Hooghly",
                        "Nadia",
                        "Dakshin Dinajpur",
                        "Purulia",
                        "Uttar Dinajpur",
                        "Siliguri"};
                    ArrayAdapter<String> array0= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, ChoosefromHere);
                ArrayAdapter<String> array1= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, AndhraPradesh);
                ArrayAdapter<String> array2= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, ArunachalPradesh);
                ArrayAdapter<String> array3= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Assam);
                ArrayAdapter<String> array4= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Bihar);
                ArrayAdapter<String> array5= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Chhattisgarh);
                ArrayAdapter<String> array6= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Chandigarh);
                ArrayAdapter<String> array7= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Delhi);
                ArrayAdapter<String> array8= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Goa);
                ArrayAdapter<String> array9= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Gujarat);
                ArrayAdapter<String> array10= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Haryana);
                ArrayAdapter<String> array11= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, HimachalPradesh);
                ArrayAdapter<String> array12= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, JammuandKashmir);
                ArrayAdapter<String> array13= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Jharkhand);
                ArrayAdapter<String> array14= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Karnataka);
                ArrayAdapter<String> array15= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Kerala);
                ArrayAdapter<String> array16= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, MadhyaPradesh);
                ArrayAdapter<String> array17= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Maharashtra);
                ArrayAdapter<String> array18= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Manipur);
                ArrayAdapter<String> array19= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Meghalaya);
                ArrayAdapter<String> array20= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Mizoram);
                ArrayAdapter<String> array21= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Nagaland);
                ArrayAdapter<String> array22= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Orissa);
                ArrayAdapter<String> array23= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Punjab);
                ArrayAdapter<String> array24= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Rajasthan);
                ArrayAdapter<String> array25= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Sikkim);
                ArrayAdapter<String> array26= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, TamilNadu);
                ArrayAdapter<String> array27= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Telangana);
                ArrayAdapter<String> array28= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Tripura);
                ArrayAdapter<String> array29= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, UttarPradesh);
                ArrayAdapter<String> array30= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, Uttarakhand);
                ArrayAdapter<String> array31= new ArrayAdapter<String>(farmerRegis.this,android.R.layout.simple_spinner_item, WestBengal);

                    array0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array14.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array15.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array16.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array17.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array18.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array19.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array20.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array21.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array22.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array23.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array24.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array25.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array26.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array27.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array28.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array29.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array30.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                array31.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                if(position == 0){
                    district.setAdapter(array0);
                }
                if(position == 1){
                    district.setAdapter(array1);
                }
                if(position == 2){
                    district.setAdapter(array2);
                }
                if(position == 3){
                    district.setAdapter(array3);
                }
                if(position == 4){
                    district.setAdapter(array4);
                }
                if(position == 5){
                    district.setAdapter(array5);
                }
                if(position == 6){
                    district.setAdapter(array6);
                }
                if(position == 7){
                    district.setAdapter(array7);
                }
                if(position == 8){
                    district.setAdapter(array8);
                }
                if(position == 9){
                    district.setAdapter(array9);
                }
                if(position == 10){
                    district.setAdapter(array10);
                }
                if(position == 11){
                    district.setAdapter(array11);
                }
                if(position == 12){
                    district.setAdapter(array12);
                }
                if(position == 13){
                    district.setAdapter(array13);
                }
                if(position == 14){
                    district.setAdapter(array14);
                }
                if(position == 15){
                    district.setAdapter(array15);
                }
                if(position == 16){
                    district.setAdapter(array16);
                }
                if(position == 17){
                    district.setAdapter(array17);
                }
                if(position == 18){
                    district.setAdapter(array18);
                }
                if(position == 19){
                    district.setAdapter(array19);
                }
                if(position == 20){
                    district.setAdapter(array20);
                }
                if(position == 21){
                    district.setAdapter(array21);
                }
                if(position == 22){
                    district.setAdapter(array22);
                }
                if(position == 23){
                    district.setAdapter(array23);
                }
                if(position == 24){
                    district.setAdapter(array24);
                }
                if(position == 25){
                    district.setAdapter(array25);
                }
                if(position == 26){
                    district.setAdapter(array26);
                }
                if(position == 27){
                    district.setAdapter(array27);
                }
                if(position == 28){
                    district.setAdapter(array28);
                }
                if(position == 29){
                    district.setAdapter(array29);
                }
                if(position == 30){
                    district.setAdapter(array30);
                }
                    if(position == 31){
                            district.setAdapter(array31);
                    }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
            register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            if(firstName.getText().toString().isEmpty()||lastName.getText().toString().isEmpty()||
                                    pinCode.getText().toString().isEmpty() ||address.getText().toString().isEmpty() ||
                                    pnumber.getText().toString().isEmpty() || state.getSelectedItem().toString().isEmpty()||
                                    district.getSelectedItem().toString().isEmpty()||pnumber.getText().toString().length()!=10)
                            {
                                   if(firstName.getText().toString().isEmpty()){
                                           Toast.makeText(getApplicationContext(),"Enter your first name",Toast.LENGTH_SHORT).show();
                                   }
                                    else if(lastName.getText().toString().isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Enter your last name",Toast.LENGTH_SHORT).show();
                                    }
                                   else if(state.getSelectedItem().toString().matches("Choose from here")){
                                           Toast.makeText(getApplicationContext(),"Select your State",Toast.LENGTH_SHORT).show();
                                   }
                                   else if(district.getSelectedItem().toString().matches("Choose from here")){
                                           Toast.makeText(getApplicationContext(),"Select your District",Toast.LENGTH_SHORT).show();
                                   }
                                    else if(pinCode.getText().toString().isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Enter your PinCode",Toast.LENGTH_SHORT).show();
                                    }
                                    else if(address.getText().toString().isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Enter your address",Toast.LENGTH_SHORT).show();
                                    }
                                    else if(pnumber.getText().toString().isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Enter your Phone Number",Toast.LENGTH_SHORT).show();
                                    }
                                   else if(pnumber.getText().toString().length()!=10){
                                           Toast.makeText(getApplicationContext(),"Enter your 10 digit Phone Number",Toast.LENGTH_SHORT).show();
                                   }
                            }
                            else{

                                pushClientManager = new GCMClientManager(farmerRegis.this, SENDER_ID);

                                pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {

                                    @Override

                                    public void onSuccess(String registrationId, boolean isNewRegistration) {

                                        //Toast.makeText(getApplicationContext(), registrationId, Toast.LENGTH_SHORT).show();
                                        CreateNewUser(firstName.getText().toString(), lastName.getText().toString(), state.getSelectedItem().toString(), district.getSelectedItem().toString(), pinCode.getText().toString(), address.getText().toString(), pnumber.getText().toString(), registrationId);

                                    }

                                    @Override
                                    public void onFailure(String ex) {
                                        super.onFailure(ex);
                                        Toast.makeText(getApplicationContext(),"Retry",Toast.LENGTH_LONG).show();
                                    }

                                });

                            }
                    }
            });
    }


        private void CreateNewUser(final String firstName, final String lastName, final String state, final String district, final String pinCode, final String address, final String phoneNumber, final String regId) {
                pDialog = new ProgressDialog(farmerRegis.this);
                pDialog.setMessage("registering ...");
                pDialog.setCancelable(false);
                pDialog.show();
                // String url = "http://192.168.43.17/register_scien/register.php;

                StringRequest SpecialReq = new StringRequest(Request.Method.POST,"http://192.168.43.17/farmer/register.php",
                        new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response)
                                {
                                        hidePDialog();

                                        Toast.makeText(getApplicationContext(),"RegID: "+regId, Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(farmerRegis.this, MainActivity.class);
                                        startActivity(i);


                                }
                        },
                        new Response.ErrorListener()
                        {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                        hidePDialog();
                                        Toast.makeText(getApplicationContext(),"failure", Toast.LENGTH_LONG).show();
                                }
                        }) {
                        @Override
                        protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("FirstName", firstName);
                                params.put("LastName", lastName);
                                params.put("State", state);
                                params.put("District", district);
                                params.put("PinCode", pinCode);
                                params.put("Address", address);
                                params.put("PhoneNumber", phoneNumber);
                                params.put("regId",regId);

                                return params;
                        }
                };



                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(SpecialReq);
        }


        @Override
        public void onDestroy() {
                super.onDestroy();
                hidePDialog();
        }

        private void hidePDialog() {
                if (pDialog != null) {
                        pDialog.dismiss();
                        pDialog = null;
                }
        }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_farmer_regis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
