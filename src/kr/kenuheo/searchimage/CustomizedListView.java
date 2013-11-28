package kr.kenuheo.searchimage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class CustomizedListView extends Activity {
    // All static variables
    static String URL = "http://openapi.naver.com/search?key=352aa64aec19751e787bcfc612862f70&query=%EC%A0%9C%EC%A3%BC%EB%8F%84&target=image&start=1&display=10";
    // XML node keys
    static final String KEY_SONG = "item"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_TITLE = "title";
    static final String KEY_ARTIST = "sizewidth";
    static final String KEY_DURATION = "sizeheight";
    static final String KEY_THUMB_URL = "thumbnail";

    ListView list;
    LazyAdapter adapter;
    EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.query);

        Button searchButton = (Button) findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String query = editText.getText().toString();
                try {
                    query = URLEncoder.encode(query, "utf-8").replaceAll("\\+",
                            "%20");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                URL = "http://openapi.naver.com/search?"
                        + "key=352aa64aec19751e787bcfc612862f70"
                        + "&target=image&start=1&display=10&query=" + query;
                fillList();
            }
        });

        fillList();
    }

    public void fillList() {
        ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

        XMLParser parser = new XMLParser();

        String xml = parser.getXmlFromUrl(URL); // getting XML from URL
        Document doc = parser.getDomElement(xml); // getting DOM element

        NodeList nl = doc.getElementsByTagName(KEY_SONG);
        // looping through all song nodes <song>
        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            // adding each child node to HashMap key => value
            map.put(KEY_ID, parser.getValue(e, KEY_ID));
            map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
            map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
            map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
            map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

            // adding HashList to ArrayList
            songsList.add(map);
        }

        list = (ListView) findViewById(R.id.list);

        // Getting adapter by passing xml data ArrayList
        adapter = new LazyAdapter(this, songsList);
        list.setAdapter(adapter);

        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {

            }
        });
    }
}