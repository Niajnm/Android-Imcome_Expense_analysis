package com.example.incomeexpensemanager

import RoomDatabase.User
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.dialog_details_layout.view.*

class AdapterDeatils(
    var context: Context,
    var rdata: MutableList<User> = mutableListOf<User>()
) :
    RecyclerView.Adapter<AdapterDeatils.MyViewHolder>() {
    private val ctx = context as DetailsActivity

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //  var viewName: TextView = itemView.findViewById(R.id.textView_title)
        var viewName: TextView = itemView.findViewById(R.id.detailsTitle_id)
        var viewIncome: TextView = itemView.findViewById(R.id.detailsBdt_id)
        var viewTime: TextView = itemView.findViewById(R.id.recent_time)
        var viewTag: Chip = itemView.findViewById(R.id.chip_id)
        var viewCard: MaterialCardView = itemView.findViewById(R.id.details_cardView)
        //  var viewExpense: Chip = itemView.findViewById(R.id.totExpense_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.item_in_out_layoout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userPosition = rdata[position]

        val getMoney = userPosition.money.toString()
        val getFlag = userPosition.tag.toString()
        val getCategory = userPosition.category.toString()
        val getTitle = userPosition.bank.toString()
        val getID = userPosition.uid.toString()
        val getTime = userPosition.time.toString()

        holder.viewName.text = "$getCategory ($getTitle)"
        holder.viewTime.text = getTime

        if (getFlag == "1") {
            holder.viewIncome.text = "BDT $getMoney"
            holder.viewTag.setChipBackgroundColorResource(R.color.grn)
        } else if (getFlag == "0") {
            holder.viewTag.setChipBackgroundColorResource(R.color.mycustom)
            holder.viewTag.setChipIconResource(R.drawable.ic_baseline_do_not_disturb_on_24)
            holder.viewTag.text = "OUT"
            holder.viewIncome.text = "BDT -$getMoney"
        }
        holder.viewCard.setOnClickListener {
            showBottomSheetDialog(getTitle, getMoney, getFlag, getID, position.toString())
        }
    }

    override fun getItemCount(): Int {
        return rdata.size
    }

    private fun showBottomSheetDialog(
        getTitle: String,
        getMoney: String,
        getFlag: String,
        getID: String,
        userPosition: String
    ) {
        val bottomSheetDialog = BottomSheetDialog(context)
        val layoutInflater = LayoutInflater.from(context)
        val dialogView = layoutInflater.inflate(R.layout.dialog_details_layout, null)
        bottomSheetDialog.setContentView(dialogView)
        val date = ctx.rcvDate
        dialogView.dlg_date_id.text = date
        dialogView.dlg_option.text = getTitle

        if (getFlag == "1") {
            dialogView.dlg_money.text = "BDT $getMoney"
            dialogView.dlg_date_id.setBackgroundResource(R.color.grn)
            dialogView.dlg_money.setBackgroundResource(R.color.grnLow)
        } else if (getFlag == "0") {
//            holder.viewTag.setChipBackgroundColorResource(R.color.mycustom)
//            holder.viewTag.setChipIconResource(R.drawable.ic_baseline_do_not_disturb_on_24)
//            holder.viewTag.text = "OUT"

            dialogView.dlg_money.text = "BDT -$getMoney"
        }
        dialogView.img_edit.setOnClickListener {
            ctx.itemEdit()
//            setFragmentResultListener("key"){requestKey, bundle ->
//                val companyId = bundle.getInt("title")
//                val companyName = bundle.getString("name")
//            }

//            val bundle = Bundle()
//            bundle.putInt("title",company.company_Id)
//            bundle.putString("name",company.name)
//            setFragmentResult("key",bundle)
//            dismiss()

        }
        dialogView.img_delete.setOnClickListener {
            ctx.itemDelete(getID.toInt(), userPosition.toInt(), getMoney.toInt(), getFlag, date)
            bottomSheetDialog.dismiss()
            rdata.removeAt(userPosition.toInt())
            notifyItemRemoved(userPosition.toInt())
            notifyItemRangeChanged(userPosition.toInt(), itemCount)
        }

        dialogView.img_share.setOnClickListener {
            // implemented below
            val help = Helper()
            val bitmap = help.getScreenShotFromView(dialogView.bottomSheet_layout)
            // if bitmap is not null then
            // save it to gallery
            if (bitmap != null) {
                help.saveMediaToStorage(context, bitmap)
            }
        }

        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetDialog.show()
        dialogView.dialouge_ok.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }
}