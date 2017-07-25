package com.richardmuttett.doordashlite.restaurantlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestManager
import com.richardmuttett.doordashlite.R
import kotlinx.android.synthetic.main.restaurant_item.view.coverImage
import kotlinx.android.synthetic.main.restaurant_item.view.description
import kotlinx.android.synthetic.main.restaurant_item.view.name
import kotlinx.android.synthetic.main.restaurant_item.view.star
import kotlinx.android.synthetic.main.restaurant_item.view.status

class RestaurantListAdapter(
        private val requestManager: RequestManager,
        private val presenter: IRestaurantsPresenter) : RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = itemView.name
        val description: TextView = itemView.description
        val status: TextView = itemView.status
        val coverImage: ImageView = itemView.coverImage
        val star: ImageView = itemView.star
    }

    override fun getItemCount() = presenter.restaurantList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.restaurant_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = bindView(holder, position)

    fun bindView(holder: ViewHolder, position: Int) {
        val restaurant = presenter.restaurantList[position]

        with(restaurant) {
            requestManager.load(coverImageUrl).into(holder.coverImage)
            holder.name.text = name
            holder.description.text = description
            holder.status.text = status
            holder.star.isSelected = isFavorite

            holder.star.setOnClickListener {
                isFavorite = !isFavorite
                holder.star.isSelected = isFavorite
                presenter.updateFavorites(this)
            }
        }
    }
}
