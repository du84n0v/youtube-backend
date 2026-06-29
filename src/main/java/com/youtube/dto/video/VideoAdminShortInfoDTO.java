package com.youtube.dto.video;

import com.youtube.dto.playlist.response.PlaylistShortDTO;
import com.youtube.dto.profile.ProfileShortInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoAdminShortInfoDTO {
    private VideoShortInfoDTO shortInfo;
    private ProfileShortInfoDTO profileInfo;
    private PlaylistShortDTO playlistInfo;
}
