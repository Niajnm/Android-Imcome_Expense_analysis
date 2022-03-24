package Fragment

import RoomDatabase.DisplayItem
import RoomDatabase.User
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_itmedi.Database.DataResponse
import com.example.incomeexpensemanager.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip

class HomeAdapter(
    var context: Context,
    var rdata: List<DisplayItem>,
    val fragment: HomeFragment
) :
    RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //  var viewName: TextView = itemView.findViewById(R.id.textView_title)
        var viewName: TextView = itemView.findViewById(R.id.textDate_id)
        var viewIncome: Chip = itemView.findViewById(R.id.totIncome_id)
        var viewExpense: Chip = itemView.findViewById(R.id.totExpense_id)
        var viewCard: MaterialCardView = itemView.findViewById(R.id.item_cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.date_item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userPosition = rdata[position]
        val getdate = userPosition.day.toString()
        val getIncome = userPosition.income.toString()
        val getExpense = userPosition.expense.toString()
        holder.viewName.text = getdate
        holder.viewIncome.text = getIncome
        holder.viewExpense.text = getExpense



        holder.viewCard.setOnClickListener {
            fragment.deatailsList(getdate, getIncome, getExpense)
        }

//        val id = userPosition.bpId!!
    }

    override fun getItemCount(): Int {
        return rdata.size
    }
}