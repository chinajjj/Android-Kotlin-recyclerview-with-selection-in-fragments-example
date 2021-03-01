package ru.pcs.cs

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout

class MainFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ):View {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView:RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = Adapter();
        recyclerView.adapter = adapter
        val list = arrayListOf<String>()
        for (i in 1..100) {
            list.add("Chat " + i.toString())
        }
        adapter.list = list
        adapter.notifyDataSetChanged()


        val selectionTracker = SelectionTracker.Builder<Long>(
                "mySelection",
                recyclerView,
                StableIdKeyProvider(recyclerView),
                MyItemDetailsLookup(recyclerView),
                StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
        ).build()
        adapter.selectionTracker = selectionTracker

        val button: Button
        button = view.findViewById(R.id.button2)
        button.setOnClickListener {
            val selection = selectionTracker.selection
            val iterator = selection.iterator()
            var cur_id:Long = 0
            var message = ""
            while(iterator.hasNext()) {
                cur_id = iterator.next()
                message += list[cur_id.toInt()] + "; "
            }
            selectionTracker.clearSelection()
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }


        return view
    }

}